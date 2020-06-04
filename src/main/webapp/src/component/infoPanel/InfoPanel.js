import React from 'react';
import "../css/InfoPanel.css"
import ElementsCounter from "./ElementsCounter";
import ElementDisplay from "./ElementDisplay";

class InfoPanel extends React.Component {

    render() {
        return (
            <div className="App-info-panel">
                <ElementsCounter entityName={"nodes"} count={this.props.nodesCount}/>
                <ElementsCounter entityName={"edges"} count={this.props.edgesCount}/>
                <div className="App-line-split"/>
                <ElementDisplay element={this.props.selectedElement}/>

            </div>
        );
    }
}

export default InfoPanel;
