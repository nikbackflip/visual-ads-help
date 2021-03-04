import React from "react";
import {Box, Grid, IconButton, Popover, Typography} from "@material-ui/core";
import EditIcon from '@material-ui/icons/Edit';
import CheckIcon from '@material-ui/icons/Check';
import CloseIcon from '@material-ui/icons/Close';
import HelpIcon from '@material-ui/icons/Help';

class ViewControlPanel extends React.Component {
    okRef = null;

    constructor(props) {
        super(props);
        this.state = {
            errorDisplayed: false
        };
    }

    editButton = () => {
        return (
            <IconButton
                size="small"
                key="edit"
                onClick={this.props.initEdit}
                color="secondary"
            >
                <EditIcon />
            </IconButton>
        )
    }

    okButton = () => {
        return (
            <IconButton
                ref={ref => {
                    this.okRef = ref;
                }}
                size="small"
                key="ok"
                onClick={() => {
                    this.setState({errorDisplayed: false});
                    this.props.updateGraph();
                }}
                color="secondary"
            >
                <CheckIcon />
            </IconButton>
        )
    }

    cancelButton = () => {
        return (
            <IconButton
                size="small"
                key="cancel"
                onClick={this.props.cancelEdit}
                color="secondary"
            >
                <CloseIcon />
            </IconButton>
        )
    }

    infoButton = () => {
        return (
            <IconButton
                size="small"
                key="info"
                onClick={(event) => {
                    this.setState({
                        infoAnchor: event.currentTarget
                    })
                }}
                color="secondary"
            >
                <HelpIcon />
            </IconButton>
        )
    }

    text = (text) => {
        return (
            <Box maxWidth={300}>
                <Typography variant="body2">
                    {text}
                </Typography>
            </Box>
        )
    }

    render() {
        let buttons;
        if (this.props.editMode) {
            buttons = [this.okButton(), this.cancelButton(), this.infoButton()]
        } else {
            buttons = [this.editButton()];
        }
        return (
            <Grid container direction="row" justify="flex-start" alignItems="flex-start">
                <Grid item>
                    <Typography variant="h6">{this.props.header}</Typography>
                </Grid>
                <Grid item>
                    {buttons}
                </Grid>
                <Popover
                    open={Boolean(this.state.infoAnchor)}
                    anchorEl={this.state.infoAnchor}
                    onClose={() => {
                        this.setState({
                            infoAnchor: null
                        })
                    }}
                    anchorOrigin={{
                        vertical: 'center',
                        horizontal: 'right',
                    }}
                    transformOrigin={{
                        vertical: 'center',
                        horizontal: 'left',
                    }}>
                    {this.text(this.props.help)}
                </Popover>
                <Popover
                    open={Boolean(this.props.errorMessage) && !this.state.errorDisplayed && Boolean(this.okRef)}
                    anchorEl={this.okRef}
                    onClose={() => {
                        this.okRef = null;
                        this.setState({
                            errorDisplayed: true
                        })
                    }}
                    anchorOrigin={{
                        vertical: 'center',
                        horizontal: 'right',
                    }}
                    transformOrigin={{
                        vertical: 'center',
                        horizontal: 'left',
                    }}>
                    {this.text(this.props.errorMessage)}
                </Popover>
            </Grid>
        )
    }

}

export default ViewControlPanel;
