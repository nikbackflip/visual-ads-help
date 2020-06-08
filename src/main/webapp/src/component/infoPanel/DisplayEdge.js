import React from "react";


class DisplayEdge extends React.Component {

    render() {
        console.log("Rendering Display Edge");

        const edge = this.props.element;
        return (
            <div>
                <p className="Info-panel-text">Id: {edge.id}</p>
                <p className="Info-panel-text">Weight: {edge.weight}</p>
                <p className="Info-panel-text">From: {edge.fromId}</p>
                <p className="Info-panel-text">To: {edge.toId}</p>
                <div className="App-line-split"/>
            </div>
        );
    }
}

export default DisplayEdge;
