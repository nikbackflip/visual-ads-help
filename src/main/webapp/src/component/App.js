import React from 'react';
import './css/App.css';
import ControlHeader from "./controlHeader/ControlHeader";
import InfoPanel from "./infoPanel/InfoPanel";
import DrawingArea from "./drawingArea/DrawingArea";
import CodePanel from "./codePanel/CodePanel";

class App extends React.Component {

    render() {
        return (
            <div className="App">
                <ControlHeader/>
                <div className="App-body">
                    <InfoPanel/>
                    <DrawingArea/>
                    <CodePanel/>
                </div>
            </div>
        );
    }
}

export default App;
