import React from 'react';
import {AppBar, Toolbar, Typography} from "@material-ui/core";

class ControlHeader extends React.Component {

    constructor(props) {
        super(props);
        this.state = {};
    }

    componentDidMount = () => {
        this.getVersion();
    };

    getVersion = () => {
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
            <AppBar position="static" color="primary">
                <Toolbar variant="dense">
                    <Typography variant="h6">
                        Visual ADS Helper
                    </Typography>
                    <Typography variant="caption" style = {{marginLeft: "auto"}}>
                        v {this.state.version}
                    </Typography>
                </Toolbar>
            </AppBar>
        );
    }
}

export default ControlHeader;
