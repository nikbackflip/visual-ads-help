import React from "react";
import {Grid, MenuItem, Select, Typography} from "@material-ui/core";


class DisplayDropdownGraphProperty extends React.Component {

    onChange = (e) => {
        this.props.updateElementProperty(this.props.propertyName, e.target.value)
    }

    render() {
        return (
            <Grid container direction="row" justify="space-between" alignItems="center" wrap="nowrap">
                <Grid item>
                    <Typography noWrap>{this.props.label}: </Typography>
                </Grid>
                <Grid item>
                    <Select
                        value={this.props.value}
                        onChange={this.onChange}
                        style = {{width: 100}}
                    >
                         {Object.entries(this.props.options).map(d => {
                             return (
                                 <MenuItem
                                     key={d[0]}
                                     value={d[0]}
                                 >
                                     {d[1]}
                                 </MenuItem>)
                         })}
                    </Select>
                </Grid>
            </Grid>
        )
    }
}

export default DisplayDropdownGraphProperty;
