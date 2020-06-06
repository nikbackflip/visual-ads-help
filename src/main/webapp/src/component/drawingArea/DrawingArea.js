import React from 'react';
import {Circle, Layer, Line, Stage, Text, Group,} from 'react-konva';
import '../css/DrawingArea.css';
import DrawingControlPanel from "./DrawingControlPanel";
import {
    MODE_NONE,
    MODE_ADD_NODE,
    MODE_ADD_EDGE,
    MODE_DEL_NODE,
    MODE_DEL_EDGE,
    colors,
    circleRadius
} from './DrawingModeConstants';
import KonvaNode from "./KonvaNode";
import KonvaEdge from "./KonvaEdge";

class DrawingArea extends React.Component {

    nodeId = 0;
    edgeId = 0;
    edgeFromNode = null;
    stageRef = null;

    nodes = []
    edges = []

    constructor(props) {
        super(props);
        this.state = {
            nodes: [],
            edges: [],
            drawingAreaMode: MODE_NONE
        };
    }

    componentDidMount = () => {
        this.updateSize();
        window.addEventListener("resize", this.updateSize);
    }

    updateSize = () => {
        this.setState({
            stageWidth: this.container.offsetWidth,
            stageHeight: this.container.offsetHeight - 45 // App-drawing-area - (Drawing-area-header + App-line-split)
        });
    }

    addNewNodeOnCoords = (x, y) => {
        const nextNodeId = this.nodeId++;
        this.nodes.push({
            id: nextNodeId,
            x: x,
            y: y,
            color: colors.sample(),
            selected: false,
            name: nextNodeId
        });
    }

    addNewEdgeFromTo = (from, to) => {
        this.edges.push({
            id: this.edgeId++,
            fromId: from.id,
            toId: to.id,
            weight: 1,
            selected: false
        });
        this.edgeFromNode = null;
    }

    clearSelection = () => {
        this.nodes.forEach(n => n.selected = false)
        this.edges.forEach(n => n.selected = false)
        this.props.handleElementSelection({});
    }

    handleStageClick = e => {
        switch (this.state.drawingAreaMode) {
            case MODE_ADD_NODE: {
                this.addNewNodeOnCoords(e.evt.layerX, e.evt.layerY);
                break;
            }
            case MODE_NONE: {
                if (e.currentTarget.clickEndShape === null) {
                    this.clearSelection();
                }
                break;
            }
            default:
                break;
        }
        this.updateGraph();
    }

    onDragMove = e => {
        const node = this.nodes.find(n => {
            return n.id === e.target.attrs.id;
        });

        node.x += e.evt.movementX;
        node.y += e.evt.movementY;

        this.updateGraph();
    }

    onDragEnd = e => {
        const node = this.nodes.find(n => {
            return n.id === e.target.attrs.id;
        });

        let x = node.x;
        let y = node.y;

        x = x < circleRadius ? circleRadius : x;
        x = x > this.state.stageWidth - circleRadius ? this.state.stageWidth - circleRadius : x;

        y = y < circleRadius ? circleRadius : y;
        y = y > this.state.stageHeight - circleRadius ? this.state.stageHeight - circleRadius : y;

        node.x = x;
        node.y = y;

        this.updateGraph();
    }

    handleCircleClick = e => {
        const clickedNode = this.nodes.find(n => {
            return n.id === e.target.attrs.id;
        });

        switch (this.state.drawingAreaMode) {
            case MODE_ADD_EDGE: {
                if (this.edgeFromNode == null) {
                    this.edgeFromNode = clickedNode;
                } else {
                    this.addNewEdgeFromTo(this.edgeFromNode, clickedNode);
                }
                break;
            }
            case MODE_DEL_NODE: {
                const index = this.nodes.indexOf(clickedNode);
                this.nodes.splice(index, 1);
                const toRemove = this.edges.filter(e => {
                    return e.fromId === clickedNode.id || e.toId === clickedNode.id;
                });
                this.edges = this.edges.filter(e => !toRemove.includes(e))
                break;
            }
            case MODE_NONE: {
                this.clearSelection();
                clickedNode.selected = true;
                this.props.handleElementSelection(clickedNode);
                break;
            }
            default:
                break;
        }
        this.updateGraph();
    }

    handleEdgeClick = e => {
        let index = this.edges.findIndex(n => {
            return n.id === e.target.attrs.id;
        });

        switch (this.state.drawingAreaMode) {
            case MODE_DEL_EDGE: {
                this.edges.splice(index, 1);
                break;
            }
            case MODE_NONE: {
                this.clearSelection();
                this.edges[index].selected = true;
                this.props.handleElementSelection(this.edges[index]);
                break;
            }
            default:
                break;
        }

        this.updateGraph();
    }

    updateGraph = () => {
        this.setState({
            nodes: this.nodes.slice(),
            edges: this.edges.slice()
        });
        this.props.handleElementsCount(this.nodes.length, this.edges.length);
    }

    handleModeChange = (mode) => {
        this.setState({
            drawingAreaMode: mode
        });
    }

    cleanStageState = () => {
        this.stageRef.clickStartShape = null;
        this.stageRef.clickEndShape = null;
    };

    render() {
        return (
            <div className="App-drawing-area"
                 ref={node => {
                     this.container = node;
                 }}>

                <DrawingControlPanel
                    modeChange={this.handleModeChange}
                />
                <div className="App-line-split"/>

                <Stage
                    ref={ref => {
                        this.stageRef = ref;
                    }}
                    width={this.state.stageWidth}
                    height={this.state.stageHeight}
                    onClick={this.handleStageClick}
                    onContentMouseup={this.cleanStageState}>
                    <Layer>
                        {this.state.edges.map(edge => {

                            const fromNode = this.state.nodes.find(n => {
                                return n.id === edge.fromId;
                            });
                            const toNode = this.state.nodes.find(n => {
                                return n.id === edge.toId;
                            });

                            return <KonvaEdge
                                key={edge.id}
                                id={edge.id}
                                from={{x: fromNode.x, y: fromNode.y}}
                                to={{x: toNode.x, y: toNode.y}}
                                selected={edge.selected}
                                weight={edge.weight}
                                handleEdgeClick={this.handleEdgeClick}
                            />
                        })}
                        {this.state.nodes.map(node => {
                            return <KonvaNode
                                key={node.id}
                                id={node.id}
                                x={node.x}
                                y={node.y}
                                color={node.color}
                                selected={node.selected}
                                name={node.name}
                                onDragMove={this.onDragMove}
                                onDragEnd={this.onDragEnd}
                                handleCircleClick={this.handleCircleClick}
                            />
                        })}
                    </Layer>
                </Stage>

            </div>
        );
    }
}

export default DrawingArea;
