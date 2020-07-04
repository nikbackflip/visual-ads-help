import React from 'react';
import './css/App.css';
import ControlHeader from "./controlHeader/ControlHeader";
import InfoPanel from "./infoPanel/InfoPanel";
import DrawingArea from "./drawingArea/DrawingArea";
import CodePanel from "./codePanel/CodePanel";
import ResizablePanels from "resizable-panels-react";

class App extends React.Component {

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

    render() {

        console.log("Rendering App");

        return (
            <div className="App">
                <ControlHeader/>
                <div className="App-body">
                    <ResizablePanels
                        displayDirection="row"
                        width="100%"
                        height="100%"
                        panelsSize={[10, 70, 20]}
                        sizeUnitMeasure="%"
                        resizerColor="#25282A"
                        resizerSize="5px"
                    >
                        <InfoPanel
                            graph={this.state.graph}
                            handleGraphUpdate={this.handleGraphUpdate}
                        />
                        <DrawingArea
                            graph={this.state.graph}
                            handleGraphUpdate={this.handleGraphUpdate}
                        />
                        <CodePanel
                            graph={this.state.graph}
                        />
                    </ResizablePanels>
                </div>
            </div>
        );
    }
}

export default App;
