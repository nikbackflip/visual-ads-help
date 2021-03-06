import React from 'react';
import {AppBar, Box, Switch, Toolbar, Typography, withTheme} from "@material-ui/core";
import NightsStayIcon from '@material-ui/icons/NightsStay';
import Brightness7Icon from '@material-ui/icons/Brightness7';

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

    changeTheme = (e) => {
        if (e) {
            this.props.handleThemeUpdate("dark")
        } else {
            this.props.handleThemeUpdate("light")
        }
    }

    render() {

        const { theme } = this.props;
        const background = {backgroundColor: theme.palette.primary.dark};

        return (
            <AppBar position="static" style={background}>
                <Toolbar variant="dense">
                    <Typography variant="h6">
                        Visual ADS Helper
                    </Typography>

                    <Box style = {{marginLeft: "auto"}}>
                        <Switch
                            checked={this.props.themeType === "dark"}
                            onChange={e => {
                                e.persist();
                                this.changeTheme(e.target.checked)
                            }}
                            icon={<NightsStayIcon/>}
                            checkedIcon={<Brightness7Icon/>}
                        />
                        <Typography variant="caption">
                            v {this.state.version}
                        </Typography>
                    </Box>
                </Toolbar>
            </AppBar>
        );
    }
}

export default withTheme(ControlHeader);
