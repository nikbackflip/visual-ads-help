import React from "react";


class DisplayNode extends React.Component {

    render() {
        console.log("Rendering Display Node");

        const node = this.props.element;
        return (
            <div>
                <p className="Info-panel-text">Id: {node.id}</p>
                <p className="Info-panel-text">Name: {node.name}</p>
                <div className="App-line-split"/>
            </div>
        );
    }
}

export default DisplayNode;
