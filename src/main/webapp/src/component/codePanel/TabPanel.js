import React from 'react';
import TabButton from "./TabButton";
import {JAVA_MODE, MATRIX_EDGE_MODE} from "./CodePanelConstants";


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
        console.log("Rendering tab panel");

        return (
            <div className="Drawing-area-header">
                <TabButton
                    mode={JAVA_MODE}
                    handleButtonClick={this.setActiveTab}
                    isActive={this.isButtonActive(JAVA_MODE)}
                />

                <TabButton
                    mode={MATRIX_EDGE_MODE}
                    handleButtonClick={this.setActiveTab}
                    isActive={this.isButtonActive(MATRIX_EDGE_MODE)}
                />
            </div>
        );
    }
}

export default TabPanel;
