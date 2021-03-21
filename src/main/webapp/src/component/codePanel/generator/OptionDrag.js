import { DragLayer } from 'react-dnd';
import React from "react";
import {Chip} from "@material-ui/core";

const layerStyles = {
    position: 'fixed',
    pointerEvents: 'none',
    zIndex: 100,
    left: 0,
    top: 0,
    width: '100%',
    height: '100%',
};

function getItemStyles(props) {
    const { initialOffset, currentOffset } = props;
    if (!initialOffset || !currentOffset) {
        return {
            display: 'none',
        };
    }
    let { x, y } = currentOffset;
    const transform = `translate(${x}px, ${y}px)`;
    return {
        transform,
        WebkitTransform: transform,
    };
}

const OptionDrag = (props) => {
    const { item, isDragging } = props;
    if (!isDragging) {
        return null;
    }
    let style = getItemStyles(props);
    return <div style={layerStyles}>
        <div style={style}>
            <Chip label={item.name}/>
        </div>
    </div>
};
export default DragLayer((monitor) => ({
    item: monitor.getItem(),
    isDragging: monitor.isDragging(),
    initialOffset: monitor.getInitialSourceClientOffset(),
    currentOffset: monitor.getSourceClientOffset(),
}))(OptionDrag);
