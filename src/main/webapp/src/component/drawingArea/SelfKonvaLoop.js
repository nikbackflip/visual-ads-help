import {Group, Line, Text} from "react-konva";
import React from "react";
import {withTheme} from "@material-ui/core";
import getDrawingColors from "../../util/ColorUtil";


class SelfKonvaLoop extends React.Component {

    shouldComponentUpdate(nextProps, nextState, nextContext) {
        return !(
            this.props.x === nextProps.x &&
            this.props.y === nextProps.y &&
            this.props.selected === nextProps.selected &&
            this.props.highlighted === nextProps.highlighted &&
            this.props.weight === nextProps.weight &&
            this.props.direction === nextProps.direction &&
            this.props.themeType === nextProps.themeType
        );
    }

    render() {
        const drawingColors = getDrawingColors(this.props.themeType);
        return <Group>
            <Line
                id={this.props.id}
                points={[this.props.x, this.props.y, this.props.x-50, this.props.y-25, this.props.x-50, this.props.y+25, this.props.x, this.props.y]}
                strokeWidth={2}
                stroke={this.props.selected || this.props.highlighted ? drawingColors.selected : drawingColors.edge}
                fill={this.props.selected || this.props.highlighted ? drawingColors.selected : drawingColors.edge}
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
                fill={drawingColors.text}
                stroke={drawingColors.text}
                fontFamily={"Verdana, monospace"}
            />
        </Group>
    }

}

export default withTheme(SelfKonvaLoop);

