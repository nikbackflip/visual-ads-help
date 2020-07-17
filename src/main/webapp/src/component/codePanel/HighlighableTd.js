import React from "react";


class HighlighableTd extends React.Component {

    render() {
        let highlight = "";
        if (this.props.halfHighlighted) highlight = "Code-panel-highlight-half";
        if (this.props.highlighted) highlight = "Code-panel-highlight-full";
        if (this.props.selected) highlight = "Code-panel-highlight-full";
        return (
            <td
                className={"Code-panel-tbody " + highlight}
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