import React from 'react';
import {Circle, Layer, Line, Stage, Text, Group,} from 'react-konva';
import '../css/DrawingArea.css';
import DrawingControlPanel from "./DrawingControlPanel";
import {MODE_NONE, MODE_ADD_NODE, MODE_ADD_EDGE, MODE_DEL_NODE, MODE_DEL_EDGE} from './DrawingModeConstants';

const colors = [
    '#FF7EC7',
    '#BF42FF',
    '#8A4BFF',
    '#4C80FF'];

const circleRadius = 20;

class DrawingArea extends React.Component {

    nodeId = 0;
    edgeId = 0;
    selectedNode = null;

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

    handleStageClick = e => {
        switch (this.state.drawingAreaMode) {
            case MODE_ADD_NODE:
                const newNodes = this.state.nodes.slice();
                const {layerY, layerX} = e.evt;
                newNodes.push({
                    id: this.nodeId,
                    x: layerX,
                    y: layerY,
                    color: colors.sample(),
                    selected: false,
                    name: this.nodeId
                });
                this.nodeId++;
                this.setState({
                    nodes: newNodes,
                });
                break;
            default:
                break;
        }
    }

    onDragMove = e => {
        const nodes = this.state.nodes.slice();
        const node = nodes.find(n => {
            return n.id === e.target.attrs.id;
        });

        node.x += e.evt.movementX;
        node.y += e.evt.movementY;

        this.setState({
            nodes: nodes,
        });
    }

    onDragEnd = e => {
        const nodes = this.state.nodes.slice();
        const node = nodes.find(n => {
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

        this.setState({
            nodes: nodes,
        });
    }

    handleCircleClick = e => {
        const nodes = this.state.nodes.slice();
        let index = nodes.findIndex(n => {
            return n.id === e.target.attrs.id;
        });

        switch (this.state.drawingAreaMode) {
            case MODE_ADD_EDGE:
                if (this.selectedNode == null) {
                    this.selectedNode = nodes[index];
                } else {
                    const node = nodes[index];
                    const updatedEdges = this.state.edges.slice();
                    updatedEdges.push({
                        id: this.edgeId++,
                        from: this.selectedNode,
                        to: node,
                        weight: 1
                    });
                    this.setState({
                        edges: updatedEdges,
                    });
                    this.selectedNode = null;
                }
                break;
            case MODE_DEL_NODE:
                const nodeToRemove = nodes[index];
                nodes.splice(index, 1);

                const edges = this.state.edges.slice();
                const toRemove = edges.filter(e => {
                    return e.from === nodeToRemove || e.to === nodeToRemove;
                });
                const updatedEdges = edges.filter(e => !toRemove.includes(e))

                this.setState({
                    nodes: nodes,
                    edges: updatedEdges
                });
                break;
            default:
                break;
        }
    }

    handleEdgeClick = e => {
        const edges = this.state.edges.slice();
        let index = edges.findIndex(n => {
            return n.id === e.target.attrs.id;
        });

        switch (this.state.drawingAreaMode) {
            case MODE_DEL_EDGE:
                edges.splice(index, 1);

                this.setState({
                    edges: edges
                });
                break;
            default:
                break;
        }
    }

    getCircle = (x, y, color, id, selected, name) => {
        return <Group
            key={id}
        >
            <Circle
                id={id}
                x={x}
                y={y}
                radius={circleRadius}
                fill={color}
                opacity={0.9}
                draggable={true}
                onDragMove={this.onDragMove}
                onDragEnd={this.onDragEnd}
                strokeEnabled={selected}
                strokeWidth={3}
                stroke={"black"}
                onClick={this.handleCircleClick}
            />
            <Text
                id={id}
                x={x}
                y={y}
                text={name}
                fontStyle={"bold"}
                fontFamily={"Verdana, monospace"}
                onClick={this.handleCircleClick}
                draggable={true}
                onDragMove={this.onDragMove}
                onDragEnd={this.onDragEnd}
            />
        </Group>
    }

    getEdge = (from, to, id, weight) => {

        let middle = {
            x: Math.abs((to.x - from.x) / 2 + from.x),
            y: Math.abs((to.y - from.y) / 2 + from.y)
        }

        let a = to.x - from.x;
        let b = to.y - from.y;
        let weightVisible = Math.sqrt(a * a + b * b) > 70;

        return <Group
            key={id}
        >
            <Line
                id={id}
                points={[from.x, from.y, to.x, to.y]}
                stroke="black"
                strokeWidth={3}
                onClick={this.handleEdgeClick}
            />
            <Text
                x={middle.x}
                y={middle.y}
                text={weight}
                stroke="#9775A0"
                strokeWidth={1}
                fontSize={15}
                fill="#9775A0"
                fontFamily={"Verdana, monospace"}
                visible={weightVisible}
            />
        </Group>
    }

    handleModeChange = (mode) => {
        this.setState({
            drawingAreaMode: mode
        });
    }

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
                    width={this.state.stageWidth}
                    height={this.state.stageHeight}
                    onClick={this.handleStageClick}>
                    <Layer>
                        {this.state.nodes.map(node => {
                            return (this.getCircle(node.x, node.y, node.color, node.id, node.selected, node.name));
                        })}

                        {this.state.edges.map(edge => {
                            return (this.getEdge(edge.from, edge.to, edge.id, edge.weight));
                        })}

                    </Layer>
                </Stage>

            </div>
        );
    }
}

export default DrawingArea;
