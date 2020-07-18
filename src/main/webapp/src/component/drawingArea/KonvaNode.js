import React from "react";
import {Circle, Group, Text} from "react-konva";
import {circleRadius} from "./DrawingModeConstants";


class KonvaNode extends React.Component {

    shouldComponentUpdate(nextProps, nextState, nextContext) {
        return !(this.props.x === nextProps.x &&
            this.props.y === nextProps.y &&
            this.props.name === nextProps.name &&
            this.props.selected === nextProps.selected &&
            this.props.highlighted === nextProps.highlighted
        );
    }

    render() {
        const textLength = this.props.name.toString().length;

        return <Group>
            <Circle
                id={this.props.id}
                x={this.props.x}
                y={this.props.y}
                radius={circleRadius}
                fill={this.props.selected || this.props.highlighted ? "#1d9797" : this.props.color}
                opacity={0.9}
                draggable={true}
                onDragMove={this.props.onDragMove}
                onDragEnd={this.props.onDragEnd}
                strokeEnabled={this.props.selected || this.props.highlighted}
                strokeWidth={3}
                stroke={"black"}
                onClick={this.props.handleCircleClick}
                onMouseEnter={this.props.onMouseEnter}
                onMouseLeave={this.props.onMouseLeave}
            />
            <Text
                id={this.props.id}
                x={this.props.x - (textLength * 4)}
                y={this.props.y - 4}
                text={this.props.name}
                fontStyle={"bold"}
                fontFamily={"Verdana, monospace"}
                onClick={this.props.handleCircleClick}
                draggable={true}
                onDragMove={this.props.onDragMove}
                onDragEnd={this.props.onDragEnd}
                onMouseEnter={this.props.onMouseEnter}
                onMouseLeave={this.props.onMouseLeave}
            />
        </Group>
    }

}

export default KonvaNode;
