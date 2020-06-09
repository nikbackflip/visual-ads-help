import React from "react";
import DisplayGraphProperty from "./DisplayGraphProperty";


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
                <DisplayGraphProperty
                    label="Id"
                    propertyName="id"
                    value={edge.id}
                    readOnly={true}
                    updateElementProperty={this.updateElement}
                />
                <DisplayGraphProperty
                    label="Weight"
                    propertyName="weight"
                    value={edge.weight}
                    readOnly={false}
                    updateElementProperty={this.updateElement}
                />
                <DisplayGraphProperty
                    label="From"
                    propertyName="fromId"
                    value={edge.fromId}
                    readOnly={true}
                    updateElementProperty={this.updateElement}
                />
                <DisplayGraphProperty
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
