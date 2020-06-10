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
        console.log("Rendering Display Edge");

        const edge = this.props.element;
        return this.props.element == null ? <div/> : (
            <div>
                <DisplayTextGraphProperty
                    label="Id"
                    propertyName="id"
                    value={edge.id}
                    readOnly={true}
                    updateElementProperty={this.updateElement}
                />
                <DisplayTextGraphProperty
                    label="Weight"
                    propertyName="weight"
                    value={edge.weight}
                    updateElementProperty={this.updateElement}
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
                    propertyName="fromId"
                    value={edge.fromId}
                    readOnly={true}
                    updateElementProperty={this.updateElement}
                />
                <DisplayTextGraphProperty
                    label="To"
                    propertyName="toId"
                    value={edge.toId}
                    readOnly={true}
                    updateElementProperty={this.updateElement}
                />

                <div className="App-line-split"/>
            </div>
        );
    }
}

export default DisplayEdge;
