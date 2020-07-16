import React from "react";
import {Group, Arrow, Text} from "react-konva";
import {
    BOTH_DIRECTIONS,
    circleRadius,
    NO_DIRECTIONS,
    REVERSE_DIRECTION
} from "./DrawingModeConstants";


class KonvaEdge extends React.Component {

    shouldComponentUpdate(nextProps, nextState, nextContext) {
        return !(
            this.props.from.x === nextProps.from.x &&
            this.props.from.y === nextProps.from.y &&
            this.props.to.x === nextProps.to.x &&
            this.props.to.y === nextProps.to.y &&
            this.props.selected === nextProps.selected &&
            this.props.weight === nextProps.weight &&
            this.props.direction === nextProps.direction
        );
    }

    render() {
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


        let points = this.props.direction === REVERSE_DIRECTION ?
            [toXdash, toYdash, fromXdash, fromYdash] :
            [fromXdash, fromYdash, toXdash, toYdash];

        return <Group>
            <Arrow
                id={this.props.id}
                points={points}
                stroke={this.props.selected ? "#1d9797" : "black"}
                fill={this.props.selected ? "#1d9797" : "black"}
                strokeWidth={3}
                onClick={this.props.handleEdgeClick}
                pointerAtBeginning={this.props.direction === BOTH_DIRECTIONS}
                pointerWidth={this.props.direction === NO_DIRECTIONS ? 0 : 10}
            />
            <Text
                x={middle.x}
                y={middle.y}
                text={this.props.weight}
                strokeWidth={1}
                fontSize={18}
                fill={"#1d9797"}
                stroke={"#1d9797"}
                fontFamily={"Verdana, monospace"}
                visible={weightVisible}
            />
        </Group>
    }

}

export default KonvaEdge;
