import React from "react";
import {dark, light} from "../../util/ColorUtil";


class HighlighableTd extends React.Component {

    render() {
        let highlight = {};
        if (this.props.focused || this.props.halfFocused) {
            highlight.backgroundColor = light.palette.secondary.dark;
            highlight.opacity = this.props.focused ? 1 : 0.5;
        }
        return (
            <td
                style={highlight}
                className={"Code-panel-tbody"}
                onMouseEnter={this.props.highlight}
                onMouseLeave={this.props.unhighlight}
                onClick={this.props.select}
            >
                {this.props.value}
            </td>
        )
    }

}

export default HighlighableTd;
