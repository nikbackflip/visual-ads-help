import React from 'react';
import './css/App.css';
import ControlHeader from "./controlHeader/ControlHeader";
import InfoPanel from "./infoPanel/InfoPanel";
import DrawingArea from "./drawingArea/DrawingArea";
import CodePanel from "./codePanel/CodePanel";

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
                </div>
            </div>
        );
    }
}

export default App;
