import React from 'react';

import 'codemirror/lib/codemirror.css';
import 'codemirror/theme/darcula.css';
import 'codemirror/mode/clike/clike.js';
import '../css/CodePanel.css';
import TabPanel from "./TabPanel";
import JavaTab from "./JavaTab";
import {JAVA_MODE, MATRIX_EDGE_MODE, TASKS_MODE} from "./CodePanelConstants";
import MatrixListTab from "./MatrixListTab";
import TasksTab from "./TasksTab";


class CodePanel extends React.Component {

    constructor(props) {
        super(props);
        this.state = {activeTab: JAVA_MODE};
    }

    handleTabChange = (tab) => {
        this.setState({
            activeTab: tab
        });
    }

    render() {
        let activeTabRender;
        switch (this.state.activeTab) {
            case JAVA_MODE:
                activeTabRender = <JavaTab
                    graph={this.props.graph}
                />
                break;
            case MATRIX_EDGE_MODE: {
                activeTabRender = <MatrixListTab className="Code-panel-whole-height"
                    graph={this.props.graph}
                    config={this.props.config}
                    handleGraphUpdate={this.props.handleGraphUpdate}
                />
                break;
            }
            case TASKS_MODE: {
                activeTabRender = <TasksTab
                    graph={this.props.graph}
                    config={this.props.config}
                />
                break;
            }
        }

        return (
            <div className="App-code-panel">
                <TabPanel
                    tabChange={this.handleTabChange}
                />
                <div className="App-line-split"/>
                {activeTabRender}
            </div>
        );
    }
}

export default CodePanel;
