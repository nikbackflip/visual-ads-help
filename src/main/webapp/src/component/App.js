import React from 'react';
import './css/App.css';
import ControlHeader from "./ControlHeader";
import InfoPanel from "./InfoPanel";
import DrawingArea from "./DrawingArea";
import CodePanel from "./CodePanel";

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
