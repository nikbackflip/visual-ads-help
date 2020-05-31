import React from 'react';
import {Stage, Layer, Circle} from 'react-konva';

const colors = [
    '#FF7EC7',
    '#BF42FF',
    '#8A4BFF',
    '#4C80FF'
];
const circleRadius = 20;

class DrawingArea extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            nodes: []
        }
    }

    componentDidMount() {
        this.updateSize();
        window.addEventListener("resize", this.updateSize);
    }

    updateSize = () => {
        this.setState({
            stageWidth: this.container.offsetWidth,
            stageHeight: this.container.offsetHeight
        });
    }

    handleStageClick = e => {
        const newNodes = this.state.nodes.slice();
        newNodes.push({
            x: e.evt.layerX,
            y: e.evt.layerY,
            color: colors.sample()
        });
        this.setState({
            nodes: newNodes,
        });
    }

    dragBoundFunc(pos) {
        let newY = pos.y < circleRadius ? circleRadius : pos.y;
        newY = newY > this.state.stageHeight - circleRadius ? this.state.stageHeight - circleRadius : newY;

        let newX = pos.x < circleRadius ? circleRadius : pos.x;
        newX = newX > this.state.stageWidth - circleRadius ? this.state.stageWidth - circleRadius : newX;
        return {
            x: newX,
            y: newY,
        };
    }

    getCircle(x, y, color) {
        return <Circle
            x={x}
            y={y}
            radius={circleRadius}
            fill={color}
            opacity={0.9}
            draggable={true}
            dragBoundFunc={(pos) => this.dragBoundFunc(pos)}
        />
    }

    render() {
        return (
            <div className="App-drawing-area"
                 ref={node => {
                     this.container = node;
                 }}>

                <Stage
                    width={this.state.stageWidth}
                    height={this.state.stageHeight}
                    onClick={this.handleStageClick}>
                    <Layer>
                        {this.state.nodes.map(shape => {
                            return (this.getCircle(shape.x, shape.y, shape.color));
                        })}
                    </Layer>
                </Stage>

            </div>
        );
    }
}

export default DrawingArea;
