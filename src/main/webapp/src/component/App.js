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
            nodesCount: 0,
            edgesCount: 0,
            selectedElement: {}
        };
    }

    handleElementsCount = (nodesCount, edgesCount) => {
        this.setState({
            edgesCount: edgesCount,
            nodesCount: nodesCount
        });
    }

    handleElementSelection = (elem) => {
        this.setState({
            selectedElement: elem
        });
    }

    render() {
        return (
            <div className="App">
                <ControlHeader/>
                <div className="App-body">
                    <InfoPanel
                        nodesCount={this.state.nodesCount}
                        edgesCount={this.state.edgesCount}
                        selectedElement={this.state.selectedElement}
                    />
                    <DrawingArea
                        handleElementsCount={this.handleElementsCount}
                        handleElementSelection={this.handleElementSelection}
                    />
                    <CodePanel/>
                </div>
            </div>
        );
    }
}

export default App;
