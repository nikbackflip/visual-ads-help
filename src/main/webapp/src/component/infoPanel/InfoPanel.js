import React from 'react';
import "../css/InfoPanel.css"
import ElementsCounter from "./ElementsCounter";

class InfoPanel extends React.Component {

    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div className="App-info-panel">
                <ElementsCounter entityName={"nodes"} count={this.props.nodesCount}/>
                <ElementsCounter entityName={"edges"} count={this.props.edgesCount}/>
            </div>
        );
    }
}

export default InfoPanel;
