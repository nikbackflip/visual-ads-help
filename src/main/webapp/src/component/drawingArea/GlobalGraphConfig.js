import React from "react";
import Switch from "@material-ui/core/Switch";
import {Box, Button, ButtonGroup, Grid, Paper, Popover, Typography} from "@material-ui/core";

class GlobalGraphConfig extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            graphDirectional: false,
            graphWeighted: false,
            selfLoopsAllowed: false
        };
    }

    componentDidMount() {
       this.setState({
           graphDirectional: this.props.config.graphDirectional,
           graphWeighted: this.props.config.graphWeighted,
           selfLoopsAllowed: this.props.config.selfLoopsAllowed
       });
    }

    edit = (config, checked) => {
        let newState = Object.assign({}, this.state);
        newState[config] = checked;
        this.setState(newState);
    }

    configsUpdated = () => {
        return this.state.graphDirectional !== this.props.config.graphDirectional ||
        this.state.graphWeighted !== this.props.config.graphWeighted ||
        this.state.selfLoopsAllowed !== this.props.config.selfLoopsAllowed;
    }

    render() {
        return (
            <Popover
                open={this.props.open}
                anchorEl={this.props.anchorEl}
                onClose={this.props.cancelConfig}
                anchorOrigin={{
                    vertical: 'bottom',
                    horizontal: 'center',
                }}
                transformOrigin={{
                    vertical: 'top',
                    horizontal: 'center',
                }}>
                <Box m={2}>
                    <Paper elevation={0}>
                        <Grid container direction="row" justify="space-between" alignItems="center" wrap="nowrap" spacing={2}>
                            <Grid item>
                                <Typography>Directional</Typography>
                            </Grid>
                            <Grid item>
                                <Switch
                                    checked={this.state.graphDirectional}
                                    onChange={e => {
                                        e.persist();
                                        this.edit("graphDirectional", e.target.checked)
                                    }}
                                    size="small"
                                    color="secondary"
                                    inputProps={{ 'aria-label': 'primary checkbox' }}/>
                            </Grid>
                        </Grid>

                        <Grid container direction="row" justify="space-between" alignItems="center" wrap="nowrap">
                            <Grid item>
                                <Typography>Weighted</Typography>
                            </Grid>
                            <Grid item>
                                <Switch
                                    checked={this.state.graphWeighted}
                                    onChange={e => {
                                        e.persist();
                                        this.edit("graphWeighted", e.target.checked)
                                    }}
                                    size="small"
                                    color="secondary"
                                    inputProps={{ 'aria-label': 'primary checkbox' }}/>
                            </Grid>
                        </Grid>

                        <Grid container direction="row" justify="space-between" alignItems="center" wrap="nowrap">
                            <Grid item>
                                <Typography>Self Loops</Typography>
                            </Grid>
                            <Grid item>
                                <Switch
                                    checked={this.state.selfLoopsAllowed}
                                    onChange={e => {
                                        e.persist();
                                        this.edit("selfLoopsAllowed", e.target.checked)
                                    }}
                                    size="small"
                                    color="secondary"
                                    inputProps={{ 'aria-label': 'primary checkbox' }}/>
                            </Grid>
                        </Grid>
                        <p/>
                        <ButtonGroup variant="text">
                            <Button
                                onClick={() => {
                                    if (this.configsUpdated())
                                        this.props.applyConfig(
                                            this.state.graphDirectional,
                                            this.state.graphWeighted,
                                            this.state.selfLoopsAllowed
                                        )
                                    else this.props.cancelConfig()
                                }}>
                                Apply
                            </Button>
                            <Button onClick={this.props.cancelConfig}>
                                Cancel
                            </Button>
                        </ButtonGroup>
                    </Paper>
                </Box>
            </Popover>
        )
    }
}

export default GlobalGraphConfig;
