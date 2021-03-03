import React from "react";
import {Typography} from "@material-ui/core";


class ElementsCounter extends React.Component {

    render() {
        const label = "Total " + this.props.entityName + ": ";

        return (
            <Typography noWrap variant="h6">{label}{this.props.count}</Typography>
        );
    }
}

export default ElementsCounter;
