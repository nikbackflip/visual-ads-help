import React from "react";
import {Group, Line, Text} from "react-konva";
import {circleRadius} from "./DrawingModeConstants";


class KonvaEdge extends React.Component {

    shouldComponentUpdate(nextProps, nextState, nextContext) {
        return !(
            this.props.from.x === nextProps.from.x &&
            this.props.from.y === nextProps.from.y &&
            this.props.to.x === nextProps.to.x &&
            this.props.to.y === nextProps.to.y &&
            this.props.selected === nextProps.selected &&
            this.props.weight === nextProps.weight
        );
    }

    render() {
        console.log("Rendering edge: " + this.props.id);

        const toX = this.props.to.x;
        const toY = this.props.to.y;
        const fromX = this.props.from.x;
        const fromY = this.props.from.y;

        const middle = {
            x: Math.abs((toX - fromX) / 2 + fromX),
            y: Math.abs((toY - fromY) / 2 + fromY)
        }

        const a = toX - fromX;
        const b = toY - fromY;
        const l = Math.sqrt(a * a + b * b);
        const weightVisible = l > 70;

        const halfLength = l / 2;
        const halfLengthDash = halfLength - circleRadius;

        const toXdash = ((halfLengthDash * (toX - middle.x)) / halfLength) + middle.x;
        const toYdash = ((halfLengthDash * (toY - middle.y)) / halfLength) + middle.y;

        const fromXdash = ((halfLengthDash * (fromX - middle.x)) / halfLength) + middle.x;
        const fromYdash = ((halfLengthDash * (fromY - middle.y)) / halfLength) + middle.y;


        return <Group>
            <Line
                id={this.props.id}
                points={[fromXdash, fromYdash, toXdash, toYdash]}
                stroke={this.props.selected ? "#1d9797" : "black"}
                strokeWidth={3}
                onClick={this.props.handleEdgeClick}
            />
            <Text
                x={middle.x}
                y={middle.y}
                text={this.props.weight}
                strokeWidth={1}
                fontSize={18}
                fill={this.props.selected ? "black" : "#1d9797"}
                stroke={this.props.selected ? "black" : "#1d9797"}
                fontFamily={"Verdana, monospace"}
                visible={weightVisible}
            />
        </Group>
    }

}

export default KonvaEdge;
