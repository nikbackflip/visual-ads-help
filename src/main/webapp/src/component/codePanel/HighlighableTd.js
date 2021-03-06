import React from "react";
import {withTheme} from "@material-ui/core";


class HighlighableTd extends React.Component {

    render() {
        const color = this.props.theme.palette.secondary.dark;
        let highlight = {};
        if (this.props.focused || this.props.halfFocused) {
            highlight.backgroundColor = color;
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

export default withTheme(HighlighableTd);
