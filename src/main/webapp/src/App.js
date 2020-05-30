import React from 'react';
import './App.css';

class App extends React.Component {

    render() {
        return (
            <div className="App">
                <header className="App-header"/>
                <div className="App-line-split"/>

                <div className="App-body">
                    <div className="App-left"/>
                    <div className="App-middle"/>
                    <div className="App-right"/>
                </div>
            </div>
        );
    }
}

export default App;
