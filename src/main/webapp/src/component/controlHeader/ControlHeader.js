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
        fetch('/api/version', {
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
                    <label className="Control-header-text">
                         version: {this.state.version}
                    </label>
                </div>
                <div className="App-line-split"/>
            </div>
        );
    }
}

export default ControlHeader;
