import React from 'react';
import {Layer, Stage} from 'react-konva';
import DrawingControlPanel from "./DrawingControlPanel";
import {
    MODE_NONE,
    MODE_ADD_NODE,
    MODE_ADD_EDGE,
    MODE_DEL_NODE,
    MODE_DEL_EDGE,
    colors,
    circleRadius,
    SELF_DIRECTION,
    FORWARD_DIRECTION,
    ABSENT_DIRECTION,
    SPLIT_DIRECTION,
    NO_DIRECTIONS, BOTH_DIRECTIONS
} from './DrawingModeConstants';
import KonvaNode from "./KonvaNode";
import KonvaEdge from "./KonvaEdge";
import TempKonvaEdge from "./TempKonvaEdge";
import SelfKonvaLoop from "./SelfKonvaLoop";
import layout from "../../util/LayoutUtil";

class DrawingArea extends React.Component {

    nodeId = 0;
    edgeId = 0;
    edgeFromNode = null;
    stageRef = null;
    drawingAreaMode = MODE_NONE;

    constructor(props) {
        super(props);
        this.state = {tempEdge : {}};
    }

    resetGraph = () => {
        this.publishAndUpdateGraph([], []);
        this.nodeId = 0;
        this.edgeId = 0;
        this.resetTempEdge();
    }

    resetTempEdge = () => {
        this.edgeFromNode = null;
        this.setState({tempEdge:{}});
    }

    addNewNodeOnCoords = (x, y) => {
        const nextNodeId = this.nodeId++;
        const nodes = this.props.graph.nodes.slice();
        nodes.push({
            id: nextNodeId,
            x: x,
            y: y,
            color: colors.sample(),
            selected: false,
            highlighted: false,
            name: nextNodeId
        });
        this.publishAndUpdateGraph(nodes, this.props.graph.edges.slice());
    }

    addNewEdgeFromTo = (from, to) => {
        if (!this.edgeIsAllowed(from, to)) {
            return;
        }
        const edges = this.props.graph.edges.slice();

        if (this.isDuplicateEdge(from, to)) {
            let requestedEdge = edges.find(e => {
                return (e.fromId === from.id && e.toId === to.id)
            });
            if (requestedEdge.direction === ABSENT_DIRECTION) {
                let pairEdgeIndex = edges.findIndex(e => {
                    return (e.id === requestedEdge.pairId)
                });
                let pairEdge = Object.assign({}, edges[pairEdgeIndex]);

                requestedEdge.direction = SPLIT_DIRECTION;
                pairEdge.direction = SPLIT_DIRECTION;
                requestedEdge.weight = 1;
                edges[pairEdgeIndex] = pairEdge;
                this.resetTempEdge();
                this.publishAndUpdateGraph(this.props.graph.nodes.slice(), edges);
            }
            return;
        }

        const newEdge = {
            id: this.edgeId++,
            fromId: from.id,
            toId: to.id,
            weight: 1,
            selected: false,
            highlighted: false,
            direction: this.props.config.graphDirectional ? FORWARD_DIRECTION : NO_DIRECTIONS
        };
        const pairEdge = {
            id: this.edgeId++,
            fromId: to.id,
            toId: from.id,
            weight: this.props.config.graphDirectional ? 0 : 1,
            selected: false,
            highlighted: false,
            direction: this.props.config.graphDirectional ? ABSENT_DIRECTION : NO_DIRECTIONS
        };

        newEdge.pairId = pairEdge.id;
        pairEdge.pairId = newEdge.id;

        if (this.isSelfLoop(from, to)) {
            newEdge.pairId = newEdge.id;
            newEdge.direction = SELF_DIRECTION;
            edges.push(newEdge);
        } else {
            edges.push(newEdge);
            edges.push(pairEdge);
        }
        this.resetTempEdge();
        this.publishAndUpdateGraph(this.props.graph.nodes.slice(), edges);
    }

    handleStageClick = e => {
        switch (this.drawingAreaMode) {
            case MODE_ADD_NODE: {
                this.addNewNodeOnCoords(e.evt.layerX, e.evt.layerY);
                break;
            }
            case MODE_NONE: {
                if (e.currentTarget.clickEndShape === null) {
                    const nodes = this.props.graph.nodes.slice();
                    const edges = this.props.graph.edges.slice();
                    nodes.forEach(n => n.selected = false);
                    edges.forEach(n => n.selected = false);
                    this.publishAndUpdateGraph(nodes, edges);
                }
                break;
            }
            default:
                break;
        }
    }

    handleStageDoubleClick = () => {
        this.resetTempEdge();
    }

    onDragMove = e => {
        const nodes = this.props.graph.nodes.slice();
        const node = nodes.find(n => {
            return n.id === e.target.attrs.id;
        });

        node.x += e.evt.movementX;
        node.y += e.evt.movementY;

        this.publishAndUpdateGraph(nodes, this.props.graph.edges.slice());
    }

    onDragEnd = e => {
        const nodes = this.props.graph.nodes.slice();
        const node = nodes.find(n => {
            return n.id === e.target.attrs.id;
        });

        let x = node.x;
        let y = node.y;

        x = x < circleRadius ? circleRadius : x;
        x = x > this.props.stage.width - circleRadius - 3 ? this.props.stage.width - circleRadius - 3 : x;

        y = y < circleRadius ? circleRadius : y;
        y = y > this.props.stage.height - circleRadius - 3 ? this.props.stage.height - circleRadius - 3 : y;

        node.x = x;
        node.y = y;

        this.publishAndUpdateGraph(nodes, this.props.graph.edges.slice());
    }

    onMouseOverNode = e => {
        this.clearHighlight();

        this.props.graph.nodes.find(n => {
            return n.id === e.target.attrs.id;
        }).highlighted = true;

        this.publishAndUpdateGraph(this.props.graph.nodes, this.props.graph.edges);
    }

    onMouseOverEdge = ev => {
        this.clearHighlight();

        const hovered = this.props.graph.edges.find(e => {
            return e.id === ev.target.attrs.id;
        })
        const hoveredPair = this.props.graph.edges.find(e => {
            return e.id === hovered.pairId;
        })
        hovered.highlighted = true;
        hoveredPair.highlighted = true;
        this.publishAndUpdateGraph(this.props.graph.nodes, this.props.graph.edges);
    }

    onMouseLeave = () => {
        this.clearHighlight();
        this.publishAndUpdateGraph(this.props.graph.nodes, this.props.graph.edges);
    }

    clearHighlight = () => {
        this.props.graph.nodes.forEach(n => n.highlighted = false);
        this.props.graph.edges.forEach(n => n.highlighted = false);
    }

    handleCircleClick = e => {
        const nodes = this.props.graph.nodes.slice();
        const clickedNode = nodes.find(n => {
            return n.id === e.target.attrs.id;
        });

        switch (this.drawingAreaMode) {
            case MODE_ADD_EDGE: {
                if (this.edgeFromNode == null) {
                    this.edgeFromNode = clickedNode;
                    this.setState({
                        tempEdge: {
                            from: clickedNode
                        }});
                } else {
                    this.addNewEdgeFromTo(this.edgeFromNode, clickedNode);
                }
                break;
            }
            case MODE_DEL_NODE: {
                let edges = this.props.graph.edges.slice();

                const index = nodes.indexOf(clickedNode);
                nodes.splice(index, 1);
                const toRemove = edges.filter(e => {
                    return e.fromId === clickedNode.id || e.toId === clickedNode.id;
                });
                edges = edges.filter(e => !toRemove.includes(e));

                //fix ids
                nodes.slice(index).forEach(n => {
                    n.id--;
                    n.name = n.id;
                });
                edges.forEach(e => {
                    if (e.fromId >= index) e.fromId--;
                    if (e.toId >= index) e.toId--;
                })
                this.nodeId--;

                this.publishAndUpdateGraph(nodes, edges);
                break;
            }
            case MODE_NONE: {
                const nodes = this.props.graph.nodes.slice();
                const edges = this.props.graph.edges.slice();
                nodes.forEach(n => n.selected = false);
                edges.forEach(n => n.selected = false);

                const clicked = nodes.find(n => {
                    return n.id === e.target.attrs.id;
                });
                clicked.selected = true;

                this.publishAndUpdateGraph(nodes, edges);
                break;
            }
            default:
                break;
        }
    }

    handleEdgeClick = e => {
        let index = this.props.graph.edges.findIndex(n => {
            return n.id === e.target.attrs.id;
        });

        switch (this.drawingAreaMode) {
            case MODE_DEL_EDGE: {
                const edges = this.props.graph.edges.slice();
                let edge = edges.find(n => {
                    return n.id === e.target.attrs.id;
                });

                if (edge.direction !== SPLIT_DIRECTION) {
                    edges.splice(index, 1);
                    if (edge.direction !== SELF_DIRECTION) {
                        let pair = edges.findIndex(n => {
                            return n.id === edge.pairId;
                        });
                        edges.splice(pair, 1);
                    }
                } else {
                    let pair = edges.find(n => {
                        return n.id === edge.pairId;
                    });
                    edge.direction = ABSENT_DIRECTION;
                    pair.direction = FORWARD_DIRECTION;
                    pair.highlighted = false;
                    pair.selected = false;
                }
                this.publishAndUpdateGraph(this.props.graph.nodes.slice(), edges);
                break;
            }
            case MODE_NONE: {
                const nodes = this.props.graph.nodes.slice();
                const edges = this.props.graph.edges.slice();
                nodes.forEach(n => n.selected = false);
                edges.forEach(n => n.selected = false);

                const clicked = edges.find(n => {
                    return n.id === e.target.attrs.id;
                });
                const clickedPair = edges.find(n => {
                    return n.id === clicked.pairId;
                });
                clicked.selected = true;
                clickedPair.selected = true;

                this.publishAndUpdateGraph(nodes, edges);
                break;
            }
            default:
                break;
        }
    }

    publishAndUpdateGraph = (nodes, edges) => {
        this.props.handleGraphUpdate({
            nodes: nodes,
            edges: edges
        });
    }

    handleModeChange = (mode) => {
        this.drawingAreaMode = mode;
    }

    cleanStageState = () => {
        this.stageRef.clickStartShape = null;
        this.stageRef.clickEndShape = null;
    };

    edgeIsAllowed = (from, to) => {
        const selfLoopConfigRespected = this.props.config.selfLoopsAllowed || from.id !== to.id;
        const directionalGraphConfigRespected = this.props.config.graphDirectional || !(this.isDuplicateEdge(from, to));
        return selfLoopConfigRespected && directionalGraphConfigRespected;
    }

    isDuplicateEdge = (from, to) => {
        return this.props.graph.edges.filter(e => {
            return (e.fromId === from.id && e.toId === to.id) ||
                   (e.fromId === to.id && e.toId === from.id)}).length !== 0;
    }

    isSelfLoop = (from, to) => {
        return from.id === to.id;
    }

    drawTempEdge = (e) => {
        switch (this.drawingAreaMode) {
            case MODE_ADD_EDGE: {
                if (this.edgeFromNode !== null) {
                    this.setState({
                        tempEdge: {
                            from: this.state.tempEdge.from,
                            to: {
                                x: e.evt.layerX,
                                y: e.evt.layerY
                            }
                        }
                    });
                }
                break;
            }
            default:
                if (this.edgeFromNode !== null) {
                    this.resetTempEdge();
                }
                break;
        }
    }

    customizeIfExternallyGenerated = () => {
        if (this.props.graph.nodes.length === 0) {
            return;
        }
        if (this.props.graph.nodes[0].selected === undefined) {
            this.nodeId = this.props.graph.nodes.length;
            this.edgeId = this.props.graph.edges.length;
            this.props.graph.nodes.forEach(n => {
                n.color = colors.sample();
                n.name = n.id;
                n.x = n.x === undefined ? Math.floor(Math.random() * (this.props.stage.width - circleRadius - circleRadius + 1) + circleRadius) : n.x;
                n.y = n.y === undefined ? Math.floor(Math.random() * (this.props.stage.height - circleRadius - circleRadius + 1) + circleRadius) : n.y;
                n.selected = false;
                n.highlighted = false;
            });
            this.props.graph.edges.forEach(e => {
                e.selected = false;
                e.highlighted = false;
            });
        }
    }

    updateGraphOnConfigUpdate = (config) => {
        let nodes = this.props.graph.nodes.slice();
        let edges = this.props.graph.edges.slice();

        if (!config.graphWeighted && this.props.config.graphWeighted) {
            for (let i = 0; i < this.props.graph.edges.length; i++) {
                if (edges[i].direction !== ABSENT_DIRECTION) {
                    const updatedEdge = Object.assign({}, edges[i]);
                    updatedEdge.weight = 1;
                    edges[i] = updatedEdge;
                }
            }
        }
        if (!config.graphDirectional && this.props.config.graphDirectional) {
            for (let j = 0; j < this.props.graph.edges.length; j++) {
                if (edges[j].direction === SELF_DIRECTION) continue;

                const updatedEdge = Object.assign({}, edges[j]);
                if (edges[j].direction === ABSENT_DIRECTION) {
                    updatedEdge.weight = edges.find(e => e.id === updatedEdge.pairId).weight;
                }
                updatedEdge.direction = NO_DIRECTIONS;
                edges[j] = updatedEdge;
            }
        }
        if (config.graphDirectional && !this.props.config.graphDirectional) {
            for (let k = 0; k < this.props.graph.edges.length; k++) {
                if (edges[k].direction === SELF_DIRECTION) continue;

                const updatedEdge = Object.assign({}, edges[k]);
                updatedEdge.direction = BOTH_DIRECTIONS;
                edges[k] = updatedEdge;
            }
        }
        if (!config.selfLoopsAllowed && this.props.config.selfLoopsAllowed) {
            edges = edges.filter(e => e.direction !== SELF_DIRECTION)
        }
        this.props.handleGraphUpdate({
            nodes: nodes,
            edges: edges
        })
        this.props.handleConfigUpdate(config);
    }

    layout = () => {
        const graph = {
            edges: this.props.graph.edges.slice().filter(e => e.direction !== ABSENT_DIRECTION),
            nodes: this.props.graph.nodes
        }
        layout(graph, this.props.config, this.props.stage)
            .then((graphAfterLayout) => {this.props.handleGraphUpdate(graphAfterLayout)});
    }

    render() {
        this.customizeIfExternallyGenerated();
        let excludedEdges = [];

        return (
            <div>
                <DrawingControlPanel
                    modeChange={this.handleModeChange}
                    graphReset={this.resetGraph}
                    config={this.props.config}
                    handleConfigUpdate = {this.updateGraphOnConfigUpdate}
                    layoutGraph = {this.layout}
                />

                <Stage
                    ref={ref => {
                        this.stageRef = ref;
                    }}
                    width={this.props.stage.width}
                    height={this.props.stage.height}
                    onClick={this.handleStageClick}
                    onContentMouseup={this.cleanStageState}
                    onMouseMove={this.drawTempEdge}
                    onDblClick={this.handleStageDoubleClick}>
                    <Layer>
                        <TempKonvaEdge
                            edge={this.state.tempEdge}
                            themeType={this.props.themeType}
                        />
                        {this.props.graph.edges.map(edge => {

                            if (edge.direction === ABSENT_DIRECTION || excludedEdges.find(e => e === edge.id)) return null;
                            if (edge.direction !== SPLIT_DIRECTION) excludedEdges.push(edge.pairId);

                            const fromNode = this.props.graph.nodes.find(n => {
                                return n.id === edge.fromId;
                            });
                            const toNode = this.props.graph.nodes.find(n => {
                                return n.id === edge.toId;
                            });

                            if (edge.direction === SELF_DIRECTION) {
                                return <SelfKonvaLoop
                                    key={edge.id}
                                    id={edge.id}
                                    x={fromNode.x}
                                    y={fromNode.y}
                                    selected={edge.selected}
                                    highlighted={edge.highlighted}
                                    weight={this.props.config.graphWeighted ? edge.weight : ""}
                                    handleEdgeClick={this.handleEdgeClick}
                                    onMouseEnter={this.onMouseOverEdge}
                                    onMouseLeave={this.onMouseLeave}
                                    themeType={this.props.themeType}
                                />
                            } else {
                                return <KonvaEdge
                                    key={edge.id}
                                    id={edge.id}
                                    from={{x: fromNode.x, y: fromNode.y}}
                                    to={{x: toNode.x, y: toNode.y}}
                                    selected={edge.selected || ((edge.direction === NO_DIRECTIONS || edge.direction === BOTH_DIRECTIONS) && this.props.graph.edges.find(e => edge.fromId === e.toId && edge.toId === e.fromId).selected)}
                                    highlighted={edge.highlighted || ((edge.direction === NO_DIRECTIONS || edge.direction === BOTH_DIRECTIONS) && this.props.graph.edges.find(e => edge.fromId === e.toId && edge.toId === e.fromId).highlighted)}
                                    weight={this.props.config.graphWeighted ? edge.weight : ""}
                                    handleEdgeClick={this.handleEdgeClick}
                                    direction={edge.direction}
                                    onMouseEnter={this.onMouseOverEdge}
                                    onMouseLeave={this.onMouseLeave}
                                    themeType={this.props.themeType}
                                />
                            }
                        })}
                        {this.props.graph.nodes.map(node => {
                            return <KonvaNode
                                key={node.id}
                                id={node.id}
                                x={node.x}
                                y={node.y}
                                color={node.color}
                                selected={node.selected}
                                highlighted={node.highlighted}
                                name={node.name}
                                onDragMove={this.onDragMove}
                                onDragEnd={this.onDragEnd}
                                handleCircleClick={this.handleCircleClick}
                                onMouseEnter={this.onMouseOverNode}
                                onMouseLeave={this.onMouseLeave}
                                themeType={this.props.themeType}
                            />
                        })}
                    </Layer>
                </Stage>

            </div>
        );
    }
}

export default DrawingArea;
