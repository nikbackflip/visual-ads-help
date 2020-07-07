import React from 'react';
import {TABS_NAMES} from "./CodePanelConstants";


class TabButton extends React.Component {

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
                {TABS_NAMES[this.props.mode]}
            </button>
        )
    }

}

export default TabButton;
