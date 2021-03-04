import React from "react";
import {black} from "color-name";
import {Line} from "react-konva";
import {lightDrawing} from "../../util/ColorUtil";


class KonvaTempEdge extends React.Component {

    render() {
        if (this.props.edge.from === undefined || this.props.edge.to === undefined) {
            return null;
        }

        const fromX = this.props.edge.from.x;
        const fromY = this.props.edge.from.y;
        const toX = this.props.edge.to.x;
        const toY = this.props.edge.to.y;

        return <Line
                points={[fromX, fromY, toX, toY]}
                stroke={lightDrawing.edge}
                strokeWidth={2}
            />
    }

}

export default KonvaTempEdge;
