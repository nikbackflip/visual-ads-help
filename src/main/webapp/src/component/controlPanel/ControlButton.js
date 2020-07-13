import React from "react";
import '../css/ControlPanel.css';


class ControlButton extends React.Component {

    handleClick = (mode) => {
        this.props.handleButtonClick(mode);
    }

    render() {
        return (
            <button
                className={this.props.isActive ?
                    "Control-panel-button-selected" : "Control-panel-button"}
                onClick={() => this.handleClick(this.props.mode)}>
                {this.props.name}
            </button>
        )
    }
}

export default ControlButton;
