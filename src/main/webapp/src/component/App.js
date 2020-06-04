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
            edgesCount: 0
        };
    }


    handleNodesCount = (delta) => {
        const newCount = this.state.nodesCount + delta;
        this.setState({
            nodesCount: newCount
        });
    }

    handleEdgesCount = (delta) => {
        const newCount = this.state.edgesCount + delta;
        this.setState({
            edgesCount: newCount
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
                    />
                    <DrawingArea
                        handleNodesCount={this.handleNodesCount}
                        handleEdgesCount={this.handleEdgesCount}
                    />
                    <CodePanel/>
                </div>
            </div>
        );
    }
}

export default App;
