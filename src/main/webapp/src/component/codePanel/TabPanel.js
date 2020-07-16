import React from 'react';
import {JAVA_MODE, MATRIX_EDGE_MODE, TABS_NAMES} from "./CodePanelConstants";
import ControlButton from "../controlPanel/ControlButton";


class TabPanel extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            activeTab: JAVA_MODE
        }
    }

    shouldComponentUpdate(nextProps, nextState, nextContext) {
        return nextState.activeTab !== this.state.activeTab;
    }

    setActiveTab = (mode) => {
        this.props.tabChange(mode);
        this.setState({
            activeTab: mode
        });
    }

    isButtonActive = (mode) => {
        return this.state.activeTab === mode;
    }

    render() {
        return (
            <div className="Control-panel-header">
                <ControlButton
                    mode={JAVA_MODE}
                    name={TABS_NAMES[JAVA_MODE]}
                    handleButtonClick={this.setActiveTab}
                    isActive={this.isButtonActive(JAVA_MODE)}
                />

                <ControlButton
                    mode={MATRIX_EDGE_MODE}
                    name={TABS_NAMES[MATRIX_EDGE_MODE]}
                    handleButtonClick={this.setActiveTab}
                    isActive={this.isButtonActive(MATRIX_EDGE_MODE)}
                />
            </div>
        );
    }
}

export default TabPanel;
