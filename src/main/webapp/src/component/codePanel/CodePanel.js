import React from 'react';

import 'codemirror/lib/codemirror.css';
import 'codemirror/theme/darcula.css';
import 'codemirror/mode/clike/clike.js';
import '../css/CodePanel.css';
import JavaTab from "./JavaTab";
import {GENERATOR_MODE, JAVA_MODE, MATRIX_EDGE_MODE, TABS_NAMES, TASKS_MODE} from "./CodePanelConstants";
import MatrixListTab from "./MatrixListTab";
import TasksTab from "./TasksTab";
import GeneratorTab from "./generator/GeneratorTab";
import {AppBar, Tab, Tabs} from "@material-ui/core";


class CodePanel extends React.Component {

    constructor(props) {
        super(props);
        this.state = {activeTab: JAVA_MODE};
    }

    handleTabChange = (event, newValue) => {
        this.setState({
            activeTab: newValue
        });
    }

    a11yProps = (index) => {
        return {
            id: `scrollable-auto-tab-${index}`,
            'aria-controls': `scrollable-auto-tabpanel-${index}`,
        };
    }

    render() {
        return (
            <div>
                <AppBar position="static" color="primary">
                    <Tabs
                        value={this.state.activeTab}
                        onChange={this.handleTabChange}
                        variant="scrollable"
                        scrollButtons="auto"
                    >
                        <Tab style={{minWidth: 120}} label={TABS_NAMES[JAVA_MODE]} {...this.a11yProps(JAVA_MODE)} />
                        <Tab style={{minWidth: 120}} label={TABS_NAMES[MATRIX_EDGE_MODE]} {...this.a11yProps(MATRIX_EDGE_MODE)} />
                        <Tab style={{minWidth: 120}} label={TABS_NAMES[TASKS_MODE]} {...this.a11yProps(TASKS_MODE)} />
                        <Tab style={{minWidth: 120}} label={TABS_NAMES[GENERATOR_MODE]} {...this.a11yProps(GENERATOR_MODE)} />
                    </Tabs>
                </AppBar>
                <TabPanel value={this.state.activeTab} index={0}>
                    <JavaTab
                        graph={this.props.graph}
                        themeType={this.props.themeType}
                    />
                </TabPanel>
                <TabPanel value={this.state.activeTab} index={1}>
                    <MatrixListTab
                        graph={this.props.graph}
                        config={this.props.config}
                        handleGraphUpdate={this.props.handleGraphUpdate}
                        stage={this.props.stage}
                    />
                </TabPanel>
                <TabPanel value={this.state.activeTab} index={2}>
                    <TasksTab
                        graph={this.props.graph}
                        config={this.props.config}
                        handleGraphUpdate={this.props.handleGraphUpdate}
                    />
                </TabPanel>
                <TabPanel value={this.state.activeTab} index={3}>
                    <GeneratorTab
                        graph={this.props.graph}
                        config={this.props.config}
                        handleGraphUpdate={this.props.handleGraphUpdate}
                        handleConfigUpdate={this.props.handleConfigUpdate}
                        stage={this.props.stage}
                    />
                </TabPanel>
            </div>
        );
    }
}

class TabPanel extends React.Component {
    render() {
        return <div
                role="tabpanel"
                hidden={this.props.value !== this.props.index}
                id={`simple-tabpanel-${this.props.index}`}
                aria-labelledby={`simple-tab-${this.props.index}`}
            >
                {this.props.value === this.props.index && (this.props.children)}
        </div>
    }

}


export default CodePanel;
