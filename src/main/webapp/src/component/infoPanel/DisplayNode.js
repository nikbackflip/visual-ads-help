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
                    value={node.name}
                    readOnly={true}
                />
                <div className="App-line-split"/>
            </div>
        );
    }
}

export default DisplayNode;
