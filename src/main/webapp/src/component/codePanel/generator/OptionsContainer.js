import React from "react";
import {DropTarget} from 'react-dnd';
import {Box, Typography} from "@material-ui/core";

const OptionsContainer = ({ options: options, canDrop, connectDropTarget, header }) => {
    return (
        <Box
            ref={connectDropTarget}
            role="Container"
        >
            <Typography variant="h6">{header + ':'}</Typography>
            <Box
                style={{
                    minHeight: "80px",
                    borderStyle: "dashed",
                    borderRadius: "25px",
                    padding: "5px",
                    borderWidth: "2px",
                    borderColor: canDrop ? "" : "transparent"
                }}
            >
                {options}
            </Box>
        </Box>
    );
};

export default DropTarget((props) => props.accepts, {
    drop: () => {
        return ({name: 'Chip'});
    },
}, (connect, monitor) => ({
    connectDropTarget: connect.dropTarget(),
    isOver: monitor.isOver(),
    canDrop: monitor.canDrop(),
}))(OptionsContainer);
