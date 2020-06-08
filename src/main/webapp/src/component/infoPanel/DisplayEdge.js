import React from "react";


class ElementDisplay extends React.Component {

    render() {
        console.log("Rendering Display Edge");

        const e = JSON.stringify(this.props.element);
        return (
            <p className="Info-panel-text">{e}</p>
        );
    }
}

export default ElementDisplay;
