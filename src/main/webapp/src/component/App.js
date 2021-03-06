import React from 'react';
import './css/App.css';
import ControlHeader from "./controlHeader/ControlHeader";
import InfoPanel from "./infoPanel/InfoPanel";
import DrawingArea from "./drawingArea/DrawingArea";
import CodePanel from "./codePanel/CodePanel";
import PropagatingResizablePanels from "./app/PropagatingResizablePanels";
import {createMuiTheme, CssBaseline, MuiThemeProvider} from "@material-ui/core";
import {dark, light} from "../util/ColorUtil";

class App extends React.Component {

    container = React.createRef();
    resizables = React.createRef();

    constructor(props) {
        super(props);
        this.state = {
            graph: {
                nodes: [],
                edges: []
            },
            config: {
                graphDirectional: true,
                graphWeighted: true,
                selfLoopsAllowed: true
            },
            stage: {
                height: 0,
                width: 0
            },
            theme: localStorage.getItem("theme") || "light"
        };
    }

    handleThemeChange = (theme) => {
        localStorage.setItem("theme", theme);
        this.setState({
            theme: theme
        })
    }

    handleGraphUpdate = (graph) => {
        this.setState({
            graph: {
                nodes: graph.nodes,
                edges: graph.edges
            }
        });
    }

    handleConfigUpdate = (config) => {
        this.setState({
            config: config
        });
    }

    componentDidMount() {
        this.onPanelsSizeUpdate();
        window.addEventListener("resize", this.onPanelsSizeUpdate);
    }

    onPanelsSizeUpdate = () => {
        let percent = this.resizables.current.state.panelsSize[1] !== undefined ? this.resizables.current.state.panelsSize[1] : 70;
        this.setState({
            stage: {
                height: this.container.current.getBoundingClientRect().height - 96,
                width: this.container.current.getBoundingClientRect().width / 100 * percent
            }
        })
    }

    getTheme = () => {
        const theme = this.state.theme;
        if (theme === "dark") return createMuiTheme(dark);
        else return createMuiTheme(light);
    }

    render() {
        return (
            <MuiThemeProvider theme={this.getTheme()}>
                <CssBaseline />
                <div
                     ref={this.container}>
                    <ControlHeader
                        handleThemeUpdate={this.handleThemeChange}
                        themeType={this.state.theme}
                    />
                    <div className="full-height">
                        <PropagatingResizablePanels
                            displayDirection="row"
                            width="100%"
                            height="100%"
                            panelsSize={[15, 45, 40]}
                            sizeUnitMeasure="%"
                            resizerColor="#000000"
                            resizerSize="2px"
                            onUpdate={this.onPanelsSizeUpdate}
                            ref={this.resizables}
                        >
                            <InfoPanel
                                graph={this.state.graph}
                                config={this.state.config}
                                handleGraphUpdate={this.handleGraphUpdate}
                            />
                            <DrawingArea
                                graph={this.state.graph}
                                config={this.state.config}
                                handleGraphUpdate={this.handleGraphUpdate}
                                handleConfigUpdate={this.handleConfigUpdate}
                                stage={this.state.stage}
                                themeType={this.state.theme}
                            />
                            <CodePanel
                                graph={this.state.graph}
                                config={this.state.config}
                                handleGraphUpdate={this.handleGraphUpdate}
                                handleConfigUpdate={this.handleConfigUpdate}
                                stage={this.state.stage}
                                themeType={this.state.theme}
                            />
                        </PropagatingResizablePanels>
                    </div>
                </div>
            </MuiThemeProvider>
        );
    }
}

export default App;
