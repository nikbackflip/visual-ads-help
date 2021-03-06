import React from "react";
import {Line} from "react-konva";
import {withTheme} from "@material-ui/core";
import getDrawingColors from "../../util/ColorUtil";


class KonvaTempEdge extends React.Component {

    render() {
        const drawingColors = getDrawingColors(this.props.themeType);
        if (this.props.edge.from === undefined || this.props.edge.to === undefined) {
            return null;
        }

        const fromX = this.props.edge.from.x;
        const fromY = this.props.edge.from.y;
        const toX = this.props.edge.to.x;
        const toY = this.props.edge.to.y;

        return <Line
                points={[fromX, fromY, toX, toY]}
                stroke={drawingColors.edge}
                strokeWidth={2}
            />
    }

}

export default withTheme(KonvaTempEdge);
