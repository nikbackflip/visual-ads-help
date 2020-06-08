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
            },
            selectedElement: {}
        };
    }

    handleGraphUpdate = (graph) => {
        this.setState({
            graph: graph
        });
    }

    handleElementSelection = (elemId) => {
        this.setState({
            selectedElement: elemId
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
                        selectedElement={this.state.selectedElement}
                    />
                    <DrawingArea
                        handleElementSelection={this.handleElementSelection}
                        handleGraphUpdate={this.handleGraphUpdate}
                    />
                    <CodePanel/>
                </div>
            </div>
        );
    }
}

export default App;
