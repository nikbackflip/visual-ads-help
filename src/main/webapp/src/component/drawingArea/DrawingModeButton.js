import React from "react";
import {BUTTON_NAMES} from "./DrawingModeConstants";


class DrawingControlButton extends React.Component {

    constructor(props) {
        super(props);
    }

    handleClick = (mode) => {
        this.props.handleButtonClick(mode);
    }

    render() {
        return (
            <button
                className={this.props.isActive ?
                    "Drawing-area-header-button-selected" :
                    "Drawing-area-header-button"}
                onClick={() => this.handleClick(this.props.mode)}>
                {BUTTON_NAMES[this.props.mode]}
            </button>
        )
    }

}

export default DrawingControlButton;
