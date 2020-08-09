import React from 'react';
import './css/App.css';
import ControlHeader from "./controlHeader/ControlHeader";
import InfoPanel from "./infoPanel/InfoPanel";
import DrawingArea from "./drawingArea/DrawingArea";
import CodePanel from "./codePanel/CodePanel";
import PropagatingResizablePanels from "./app/PropagatingResizablePanels";

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
            }
        };
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
            graph: {
                nodes: [],
                edges: []
            },
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
            stageHeight: this.container.current.getBoundingClientRect().height - 90,
            stageWidth: this.container.current.getBoundingClientRect().width / 100 * percent - 10
        })
    }

    render() {
        return (
            <div className="App"
                 ref={this.container}>
                <ControlHeader/>
                <div className="App-body">
                    <PropagatingResizablePanels
                        displayDirection="row"
                        width="100%"
                        height="100%"
                        panelsSize={[15, 45, 40]}
                        sizeUnitMeasure="%"
                        resizerColor="#25282A"
                        resizerSize="5px"
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
                            stageHeight={this.state.stageHeight}
                            stageWidth={this.state.stageWidth}
                        />
                        <CodePanel
                            graph={this.state.graph}
                            config={this.state.config}
                            handleGraphUpdate={this.handleGraphUpdate}
                        />
                    </PropagatingResizablePanels>
                </div>
            </div>
        );
    }
}

export default App;
