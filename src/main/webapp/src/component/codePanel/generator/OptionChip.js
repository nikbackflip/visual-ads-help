import {Chip} from "@material-ui/core";
import React, { PureComponent } from "react";
import {DragSource} from 'react-dnd';
import {getEmptyImage} from "react-dnd-html5-backend";


class OptionChip extends PureComponent {
    componentDidMount() {
        const { connectDragPreview } = this.props;
        if (connectDragPreview) {
            connectDragPreview(getEmptyImage(), {
                captureDraggingState: true,
            });
        }
    }
    render() {
        const { isDragging, name, onDrop, connectDragSource } = this.props;
        return connectDragSource(<span>
            <Chip
                ref={connectDragSource}
                label={name}
                style={{margin: 2, visibility: isDragging ? "hidden" : ""}}
                role={'Option'}
                onClick={onDrop}
            />
        </span>);
    }
}


export default DragSource((props) => props.type, {
    beginDrag: (props) => {
        return { name: props.name, onDrop: props.onDrop };
    },
    endDrag(props, monitor) {
        const item = monitor.getItem();
        const dropped = monitor.didDrop();
        if (dropped) {
            item.onDrop();
        }
    },
}, (connect, monitor) => {
    return {
        connectDragSource: connect.dragSource(),
        connectDragPreview: connect.dragPreview(),
        isDragging: monitor.isDragging(),
    };
})(OptionChip);
