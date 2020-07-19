import {Group, Line, Text} from "react-konva";
import React from "react";


class SelfKonvaEdge extends React.Component {

    shouldComponentUpdate(nextProps, nextState, nextContext) {
        return !(
            this.props.x === nextProps.x &&
            this.props.y === nextProps.y &&
            this.props.selected === nextProps.selected &&
            this.props.highlighted === nextProps.highlighted &&
            this.props.weight === nextProps.weight &&
            this.props.direction === nextProps.direction
        );
    }

    render() {
        return <Group>
            <Line
                id={this.props.id}
                points={[this.props.x, this.props.y, this.props.x-50, this.props.y-25, this.props.x-50, this.props.y+25, this.props.x, this.props.y]}
                strokeWidth={3}
                stroke={this.props.selected || this.props.highlighted ? "#1d9797" : "black"}
                fill={this.props.selected || this.props.highlighted ? "#1d9797" : "black"}
                tension={1}
                onClick={this.props.handleEdgeClick}
                onMouseEnter={this.props.onMouseEnter}
                onMouseLeave={this.props.onMouseLeave}
            />
            <Text
                x={this.props.x-75}
                y={this.props.y-10}
                text={this.props.weight}
                strokeWidth={1}
                fontSize={18}
                fill={"#1d9797"}
                stroke={"#1d9797"}
                fontFamily={"Verdana, monospace"}
            />
        </Group>
    }

}

export default SelfKonvaEdge;

