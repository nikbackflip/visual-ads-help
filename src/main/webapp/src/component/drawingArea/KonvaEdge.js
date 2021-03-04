import React from "react";
import {Group, Arrow, Text} from "react-konva";
import {
    ABSENT_DIRECTION,
    BOTH_DIRECTIONS,
    circleRadius,
    NO_DIRECTIONS,
    SPLIT_DIRECTION
} from "./DrawingModeConstants";
import {darkDrawing, lightDrawing} from "../../util/ColorUtil";


class KonvaEdge extends React.Component {

    shouldComponentUpdate(nextProps, nextState, nextContext) {
        return !(
            this.props.from.x === nextProps.from.x &&
            this.props.from.y === nextProps.from.y &&
            this.props.to.x === nextProps.to.x &&
            this.props.to.y === nextProps.to.y &&
            this.props.selected === nextProps.selected &&
            this.props.highlighted === nextProps.highlighted &&
            this.props.weight === nextProps.weight &&
            this.props.direction === nextProps.direction
        );
    }

    render() {

        if (this.props.direction === ABSENT_DIRECTION) return null;

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

        let points = [fromXdash, fromYdash, toXdash, toYdash];
        let textCoords = {
            x: middle.x,
            y: middle.y
        }

        if (this.props.direction === SPLIT_DIRECTION) {
            // let h = l * 0.15;
            let h = 25;

            let alpha;
            if (toY < fromY) {
                alpha = Math.acos((toX - fromX) / l);
                h = -h;
            } else {
                alpha = Math.acos((fromX - toX) / l);
            }
            const arc = {
                x: middle.x + Math.sin(alpha) * h,
                y: middle.y + Math.cos(alpha) * h
            }


            let beta = Math.atan(h / halfLength);
            beta = beta > 0 ? 0.5 : -0.5;

            if (h > 0) beta = -beta;

            const fromXdashArc = (fromXdash - fromX) * Math.cos(beta) - (fromYdash - fromY) * Math.sin(beta);
            const fromYdashArc = (fromXdash - fromX) * Math.sin(beta) + (fromYdash - fromY) * Math.cos(beta);
            const toXdashArc = (toXdash - toX) * Math.cos(-beta) - (toYdash - toY) * Math.sin(-beta);
            const toYdashArc = (toXdash - toX) * Math.sin(-beta) + (toYdash - toY) * Math.cos(-beta);

            points = [fromXdashArc + fromX, fromYdashArc + fromY, arc.x, arc.y, toXdashArc + toX, toYdashArc + toY];
            textCoords = {
                x: arc.x,
                y: arc.y
            }
        }

        return <Group>
            <Arrow
                id={this.props.id}
                points={points}
                stroke={this.props.selected || this.props.highlighted ? lightDrawing.selected : lightDrawing.edge}
                fill={this.props.selected || this.props.highlighted ? lightDrawing.selected : lightDrawing.edge}
                strokeWidth={2}
                onClick={this.props.handleEdgeClick}
                tension={0.4}
                pointerAtBeginning={this.props.direction === BOTH_DIRECTIONS}
                pointerWidth={this.props.direction === NO_DIRECTIONS ? 0 : 10}
                onMouseEnter={this.props.onMouseEnter}
                onMouseLeave={this.props.onMouseLeave}
            />
            <Text
                x={textCoords.x}
                y={textCoords.y}
                text={this.props.weight}
                strokeWidth={1}
                fontSize={18}
                fill={lightDrawing.text}
                stroke={lightDrawing.text}
                fontFamily={"Verdana, monospace"}
                visible={weightVisible}
            />
        </Group>
    }

}

export default KonvaEdge;
