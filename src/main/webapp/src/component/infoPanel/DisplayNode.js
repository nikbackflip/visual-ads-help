import React from "react";


class DisplayNode extends React.Component {

    render() {
        console.log("Rendering Display Node");

        const e = JSON.stringify(this.props.element);
        return (
            <p className="Info-panel-text">{e}</p>
        );
    }
}

export default DisplayNode;
