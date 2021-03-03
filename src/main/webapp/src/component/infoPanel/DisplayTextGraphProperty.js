import React from "react";
import {Grid, Input, Typography} from "@material-ui/core";

class DisplayTextGraphProperty extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            value: this.props.value
        }
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
        if (prevProps.value !== this.props.value) {
            this.setState({value: this.props.value})
        }
    }

    onInputEnd = (e) => {
        if (this.props.inputIsValid(e.target.value)) {
            this.props.updateElementProperty(this.props.propertyName, e.target.value)
        } else {
            this.setState({
                value: this.props.value
            })
        }
    }

    onChange = (e) => {
        this.setState({
            value: e.target.value
        })
    }

    render() {
        return (
            <Grid container direction="row" justify="space-between" alignItems="center" wrap="nowrap">
                <Grid item>
                    <Typography noWrap>{this.props.label}: </Typography>
                </Grid>
                <Grid item>
                    <Input
                        style = {{width: 100}}
                        type={this.props.inputFormat == null ? "text" : this.props.inputFormat}
                        value={this.state.value}
                        onBlur={this.onInputEnd}
                        onChange={this.onChange}
                        readOnly={this.props.readOnly == null ? false : this.props.readOnly}
                        maxLength={this.props.maxLength}
                    />
                </Grid>
            </Grid>
        )
    }
}

export default DisplayTextGraphProperty;
