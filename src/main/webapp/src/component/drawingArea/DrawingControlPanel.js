import React from "react";
import {
    MODE_NONE,
    MODE_ADD_NODE,
    MODE_ADD_EDGE,
    MODE_DEL_NODE,
    MODE_DEL_EDGE,
    BUTTON_NAMES
} from './DrawingModeConstants';
import ControlButton from "../controlPanel/ControlButton";


class DrawingControlPanel extends React.Component {

    shouldComponentUpdate(nextProps, nextState, nextContext) {
        return nextState.drawingAreaMode !== this.state.drawingAreaMode;
    }

    constructor(props) {
        super(props);
        this.state = {
            drawingAreaMode: MODE_NONE
        }
    }

    setDrawingMode = (mode) => {
        this.props.modeChange(mode);
        this.setState({
            drawingAreaMode: mode
        });
    }

    isButtonActive = (mode) => {
        return this.state.drawingAreaMode === mode;
    }

    render() {

        console.log("Rendering control panel");

        return (
            <div className="Control-panel-header">
                <ControlButton
                    mode={MODE_NONE}
                    name={BUTTON_NAMES[MODE_NONE]}
                    handleButtonClick={this.setDrawingMode}
                    isActive={this.isButtonActive(MODE_NONE)}
                />

                <ControlButton
                    mode={MODE_ADD_NODE}
                    name={BUTTON_NAMES[MODE_ADD_NODE]}
                    handleButtonClick={this.setDrawingMode}
                    isActive={this.isButtonActive(MODE_ADD_NODE)}
                />

                <ControlButton
                    mode={MODE_ADD_EDGE}
                    name={BUTTON_NAMES[MODE_ADD_EDGE]}
                    handleButtonClick={this.setDrawingMode}
                    isActive={this.isButtonActive(MODE_ADD_EDGE)}
                />

                <ControlButton
                    mode={MODE_DEL_NODE}
                    name={BUTTON_NAMES[MODE_DEL_NODE]}
                    handleButtonClick={this.setDrawingMode}
                    isActive={this.isButtonActive(MODE_DEL_NODE)}
                />

                <ControlButton
                    mode={MODE_DEL_EDGE}
                    name={BUTTON_NAMES[MODE_DEL_EDGE]}
                    handleButtonClick={this.setDrawingMode}
                    isActive={this.isButtonActive(MODE_DEL_EDGE)}
                />

                <button
                    className={"Control-panel-button Drawing-area-refresh-button"}
                    onClick={this.props.graphReset}
                >
                    <i className="fa fa-refresh"/>
                </button>

            </div>

        )
    }


}

export default DrawingControlPanel;

