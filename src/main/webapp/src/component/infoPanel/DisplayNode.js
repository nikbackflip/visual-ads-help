import React from "react";
import UpdateGraphProperty from "./UpdateGraphProperty";


class DisplayNode extends React.Component {

    updateElement = (name, value) => {
        let updatedNode = Object.assign({}, this.props.element);
        updatedNode[name] = value;
        this.props.updateElement(updatedNode);
    }

    render() {
        console.log("Rendering Display Node");

        const node = this.props.element;
        return (
            <div>
                <p className="Info-panel-text">Id: {node.id}</p>
                <UpdateGraphProperty
                    label="Name"
                    propertyName="name"
                    value={node.name}
                    updateElementProperty={this.updateElement}
                />
                <div className="App-line-split"/>
            </div>
        );
    }
}

export default DisplayNode;
