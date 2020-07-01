import React from 'react';
import "../css/ControlHeader.css"

class ControlHeader extends React.Component {

    constructor(props) {
        super(props);
        this.state = {};
    }

    componentDidMount = () => {
        this.getVersion();
    };

    getVersion = () => {
        console.log("Version requested");
        fetch('/version', {
            method: 'GET'
        }).then(response => {
            if (response.ok) {
                response.json().then(json => {
                    this.setState({
                        version: json.version
                    });
                });
            }
        });
    };

    render() {
        return (
            <div>
                <div className="App-control-header">
                    <header className="Control-header-title">
                        Visual ADS Helper
                    </header>
                    <label className="Control-header-version">
                         version: {this.state.version}
                    </label>
                </div>
                <div className="App-line-split"/>
            </div>
        );
    }
}

export default ControlHeader;
