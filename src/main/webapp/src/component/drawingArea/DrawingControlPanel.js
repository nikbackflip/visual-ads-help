import React from "react";
import {
    MODE_NONE,
    MODE_ADD_NODE,
    MODE_ADD_EDGE,
    MODE_DEL_NODE,
    MODE_DEL_EDGE,
    BUTTON_NAMES
} from './DrawingModeConstants';
import GlobalGraphConfig from "./GlobalGraphConfig";
import {AppBar, Button, ButtonGroup, Grid, Tab, Tabs} from "@material-ui/core";
import LoopIcon from '@material-ui/icons/Loop';
import SettingsIcon from '@material-ui/icons/Settings';
import FilterVintageIcon from '@material-ui/icons/FilterVintage';


class DrawingControlPanel extends React.Component {

    shouldComponentUpdate(nextProps, nextState, nextContext) {
        return nextState.activeMode !== this.state.activeMode || nextState.globalConfigAnchor !== this.state.globalConfigAnchor;
    }

    constructor(props) {
        super(props);
        this.state = {
            activeMode: MODE_NONE
        }
    }

    handleTabChange = (event, newValue) => {
        this.props.modeChange(newValue);
        this.setState({
            activeMode: newValue
        });
    }

    a11yProps = (index) => {
        return {
            id: `scrollable-auto-tab-${index}`,
            'aria-controls': `scrollable-auto-tabpanel-${index}`,
        };
    }

    render() {
        return (
            <div>
                <AppBar position="static" color="default">

                    <Grid container direction="row" justify="space-between" alignItems="flex-start" color="primary" wrap="nowrap">

                        <Grid item>
                            <ButtonGroup variant="text">
                                <Button color="secondary" onClick={this.props.layoutGraph}>
                                    <FilterVintageIcon />
                                </Button>
                                <Button
                                    color="secondary"
                                    onClick={(event) => {
                                        this.setState({
                                            globalConfigAnchor: event.currentTarget
                                        })
                                    }}>
                                    <SettingsIcon />
                                </Button>
                                <Button color="secondary" onClick={this.props.graphReset}>
                                    <LoopIcon />
                                </Button>
                            </ButtonGroup>
                        </Grid>

                        <Tabs variant="scrollable" scrollButtons="auto" value={this.state.activeMode} onChange={this.handleTabChange}>
                            <Tab style={{minWidth: 72}} label={BUTTON_NAMES[MODE_NONE]} {...this.a11yProps(MODE_NONE)}/>
                            <Tab style={{minWidth: 72}} label={BUTTON_NAMES[MODE_ADD_NODE]} {...this.a11yProps(MODE_ADD_NODE)}/>
                            <Tab style={{minWidth: 72}} label={BUTTON_NAMES[MODE_ADD_EDGE]} {...this.a11yProps(MODE_ADD_EDGE)}/>
                            <Tab style={{minWidth: 72}} label={BUTTON_NAMES[MODE_DEL_NODE]} {...this.a11yProps(MODE_DEL_NODE)}/>
                            <Tab style={{minWidth: 72}} label={BUTTON_NAMES[MODE_DEL_EDGE]} {...this.a11yProps(MODE_DEL_EDGE)}/>
                        </Tabs>
                    </Grid>
                </AppBar>

                <GlobalGraphConfig
                    open={Boolean(this.state.globalConfigAnchor)}
                    config={this.props.config}
                    anchorEl={this.state.globalConfigAnchor}
                    cancelConfig={() => {
                        this.setState({
                            globalConfigAnchor: null
                        })}
                    }
                    applyConfig={(graphDirectional, graphWeighted, selfLoopsAllowed) => {
                        this.setState({
                            globalConfigAnchor: null
                        })
                        this.props.handleConfigUpdate({
                            graphDirectional: graphDirectional,
                            graphWeighted: graphWeighted,
                            selfLoopsAllowed: selfLoopsAllowed
                        });
                    }}
                />
            </div>
        )
    }

}

export default DrawingControlPanel;

