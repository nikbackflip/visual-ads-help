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
import GlobalGraphConfig from "./GlobalGraphConfig";


class DrawingControlPanel extends React.Component {

    shouldComponentUpdate(nextProps, nextState, nextContext) {
        return nextState.drawingAreaMode !== this.state.drawingAreaMode || nextState.configDisplay !== this.state.configDisplay;
    }

    constructor(props) {
        super(props);
        this.state = {
            drawingAreaMode: MODE_NONE,
            configDisplay: false
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
        return (
            <div>
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
                        className={(this.state.configDisplay ? "Control-panel-button-selected" : "Control-panel-button")
                            + " Drawing-area-config-button"}
                        onClick={() => this.setState({
                            configDisplay: !this.state.configDisplay
                        })}
                    >
                        <i className="fa fa-cog" aria-hidden="true" />
                    </button>

                    <button
                        className={"Control-panel-button Drawing-area-refresh-button"}
                        onClick={this.props.graphReset}
                    >
                        <i className="fa fa-refresh"/>
                    </button>
                </div>
                {
                    this.state.configDisplay ?
                        <GlobalGraphConfig
                            config={this.props.config}
                            cancelConfig={() => {
                                this.setState({
                                    configDisplay: false
                                })}
                            }
                            applyConfig={(graphDirectional, graphWeighted, selfEdgesAllowed) => {
                                this.setState({
                                    configDisplay: false
                                })
                                this.props.handleConfigUpdate({
                                    graphDirectional: graphDirectional,
                                    graphWeighted: graphWeighted,
                                    selfEdgesAllowed: selfEdgesAllowed
                                });
                            }}
                        />
                    : null
                }
            </div>
        )
    }

}

export default DrawingControlPanel;

