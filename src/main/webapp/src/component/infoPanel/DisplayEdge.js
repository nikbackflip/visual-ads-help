import React from "react";
import DisplayTextGraphProperty from "./DisplayTextGraphProperty";
import DisplayDropdownGraphProperty from "./DisplayDropdownGraphProperty";
import {EDGE_DIRECTION_NAMES} from "../drawingArea/DrawingModeConstants";


class DisplayEdge extends React.Component {

    updateElement = (name, value) => {
        let updatedEdge = Object.assign({}, this.props.element);
        updatedEdge[name] = value;
        this.props.updateElement(updatedEdge);
    }

    render() {
        const edge = this.props.element;
        return this.props.element == null ? <div/> : (
            <div>
                <DisplayTextGraphProperty
                    label="Weight"
                    propertyName="weight"
                    value={edge.weight}
                    updateElementProperty={this.updateElement}
                    inputIsValid={(input) => {
                        return parseFloat(input) !== 0 && !isNaN(parseFloat(input));
                    }}
                    inputFormat="number"
                />
                <DisplayDropdownGraphProperty
                    label="Direction"
                    propertyName="direction"
                    value={edge.direction}
                    updateElementProperty={this.updateElement}
                    options={EDGE_DIRECTION_NAMES}
                />
                <DisplayTextGraphProperty
                    label="From"
                    value={this.props.getNodeName(edge.fromId).name}
                    readOnly={true}
                />
                <DisplayTextGraphProperty
                    label="To"
                    value={this.props.getNodeName(edge.toId).name}
                    readOnly={true}
                />

                <div className="App-line-split"/>
            </div>
        );
    }
}

export default DisplayEdge;
