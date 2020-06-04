import React from "react";


class ElementsCounter extends React.Component {

    render() {
        const label = "Total " + this.props.entityName + ": ";

        return (
            <p className="Info-panel-label">{label}{this.props.count}</p>
        );
    }
}

export default ElementsCounter;
