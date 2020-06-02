import React from 'react';
import {Circle, Layer, Line, Stage} from 'react-konva';
import '../css/DrawingArea.css';
import {black} from "color-name";
import DrawingControlPanel from "./DrawingControlPanel";
import {MODE_NONE, MODE_ADD_NODE, MODE_ADD_EDGE} from './DrawingModeConstants';

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
                    id: this.nodeId++,
                    x: layerX,
                    y: layerY,
                    color: colors.sample(),
                    selected: false
                });
                this.setState({
                    nodes: newNodes,
                });
                break;
            default:
                break;
        }
    }

    dragBoundFunc = pos => {
        let newY = pos.y < circleRadius ? circleRadius : pos.y;
        newY = newY > this.state.stageHeight - circleRadius ? this.state.stageHeight - circleRadius : newY;

        let newX = pos.x < circleRadius ? circleRadius : pos.x;
        newX = newX > this.state.stageWidth - circleRadius ? this.state.stageWidth - circleRadius : newX;
        return {
            x: newX,
            y: newY,
        };
    }

    onDragMove = e => {
        const nodes = this.state.nodes.slice();
        const node = nodes[e.target.index];
        node.x += e.evt.movementX;
        node.y += e.evt.movementY;
        this.setState({
            nodes: nodes,
        });

    }

    handleCircleClick = e => {

        switch (this.state.drawingAreaMode) {
            case MODE_ADD_EDGE:
                if (this.selectedNode == null) {
                    const nodes = this.state.nodes.slice();
                    this.selectedNode = nodes[e.target.index];
                } else {
                    const nodes = this.state.nodes.slice();
                    const node = nodes[e.target.index];
                    const newEdges = this.state.edges.slice();
                    newEdges.push({
                        id: this.edgeId++,
                        from: this.selectedNode,
                        to: node
                    });
                    this.setState({
                        edges: newEdges,
                    });
                    this.selectedNode = null;
                }
                break;
            default:
                break;
        }
    }

    getCircle = (x, y, color, id, selected) => {
        return <Circle
            key={id}
            x={x}
            y={y}
            radius={circleRadius}
            fill={color}
            opacity={0.9}
            draggable={true}
            dragBoundFunc={this.dragBoundFunc}
            onDragMove={this.onDragMove}
            strokeEnabled={selected}
            strokeWidth={3}
            stroke={"black"}
            onClick={this.handleCircleClick}
        />
    }

    getEdge = (from, to, id) => {
        return <Line
            key={id}
            points={[from.x, from.y, to.x, to.y]}
            stroke={black}
            strokeWidth={2}
        />
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
                            return (this.getCircle(node.x, node.y, node.color, node.id, node.selected));
                        })}

                        {this.state.edges.map(edge => {
                            return (this.getEdge(edge.from, edge.to, edge.id));
                        })}

                    </Layer>
                </Stage>

            </div>
        );
    }
}

export default DrawingArea;
