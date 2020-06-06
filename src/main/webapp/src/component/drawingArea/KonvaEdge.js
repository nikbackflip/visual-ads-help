import React from "react";
import {Group, Line, Text} from "react-konva";


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

        let middle = {
            x: Math.abs((this.props.to.x - this.props.from.x) / 2 + this.props.from.x),
            y: Math.abs((this.props.to.y - this.props.from.y) / 2 + this.props.from.y)
        }

        let a = this.props.to.x - this.props.from.x;
        let b = this.props.to.y - this.props.from.y;
        let weightVisible = Math.sqrt(a * a + b * b) > 70;

        return <Group>
            <Line
                id={this.props.id}
                points={[this.props.from.x, this.props.from.y, this.props.to.x, this.props.to.y]}
                stroke={this.props.selected ? "red" : "black"}
                strokeWidth={3}
                onClick={this.props.handleEdgeClick}
            />
            <Text
                x={middle.x}
                y={middle.y}
                text={this.props.weight}
                stroke="#9775A0"
                strokeWidth={1}
                fontSize={15}
                fill="#9775A0"
                fontFamily={"Verdana, monospace"}
                visible={weightVisible}
            />
        </Group>
    }

}

export default KonvaEdge;
