import React from "react";
import DisplayTextGraphProperty from "./DisplayTextGraphProperty";


class DisplayNode extends React.Component {

    updateElement = (name, value) => {
        let updatedNode = Object.assign({}, this.props.element);
        updatedNode[name] = value;
        this.props.updateElement(updatedNode);
    }

    render() {
        const node = this.props.element;
        return this.props.element == null ? <div/> : (
            <div>
                <DisplayTextGraphProperty
                    label="Name"
                    propertyName="name"
                    value={node.name}
                    updateElementProperty={this.updateElement}
                    inputIsValid={(input) => {
                        return true;
                    }}
                    maxLength={4}
                />
                <div className="App-line-split"/>
            </div>
        );
    }
}

export default DisplayNode;
