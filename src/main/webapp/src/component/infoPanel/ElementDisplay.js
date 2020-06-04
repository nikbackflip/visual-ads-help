import React from "react";


class ElementDisplay extends React.Component {

    render() {
        const e = JSON.stringify(this.props.element);
        return (
            <p className="Info-panel-text">{e}</p>
        );
    }
}

export default ElementDisplay;
