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
        console.log("Rendering App");

        return (
            <div className="App"
                 ref={this.container}>
                <ControlHeader/>
                <div className="App-body">
                    <PropagatingResizablePanels
                        displayDirection="row"
                        width="100%"
                        height="100%"
                        panelsSize={[10, 70, 20]}
                        sizeUnitMeasure="%"
                        resizerColor="#25282A"
                        resizerSize="5px"
                        onUpdate={this.onPanelsSizeUpdate}
                        ref={this.resizables}
                    >
                        <InfoPanel
                            graph={this.state.graph}
                            handleGraphUpdate={this.handleGraphUpdate}
                        />
                        <DrawingArea
                            graph={this.state.graph}
                            handleGraphUpdate={this.handleGraphUpdate}
                            stageHeight={this.state.stageHeight}
                            stageWidth={this.state.stageWidth}
                        />
                        <CodePanel
                            graph={this.state.graph}
                        />
                    </PropagatingResizablePanels>
                </div>
            </div>
        );
    }
}

export default App;
