import React from "react";
import DisplayGraphProperty from "./DisplayGraphProperty";


class DisplayNode extends React.Component {

    updateElement = (name, value) => {
        let updatedNode = Object.assign({}, this.props.element);
        updatedNode[name] = value;
        this.props.updateElement(updatedNode);
    }

    render() {
        console.log("Rendering Display Node");

        const node = this.props.element;
        return this.props.element == null ? <div/> : (
            <div>
                <DisplayGraphProperty
                    label="Id"
                    propertyName="id"
                    value={node.id}
                    readOnly={true}
                    updateElementProperty={this.updateElement}
                    inputType="text"
                />
                <DisplayGraphProperty
                    label="Name"
                    propertyName="name"
                    value={node.name}
                    readOnly={false}
                    updateElementProperty={this.updateElement}
                    maxLength={4}
                    inputType="text"
                />
                <div className="App-line-split"/>
            </div>
        );
    }
}

export default DisplayNode;
