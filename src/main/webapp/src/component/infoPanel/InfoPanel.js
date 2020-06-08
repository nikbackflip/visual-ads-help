import React from 'react';
import "../css/InfoPanel.css"
import ElementsCounter from "./ElementsCounter";
import ElementDisplay from "./ElementDisplay";

class InfoPanel extends React.Component {

    render() {

        const nodes = this.props.graph.nodes;
        const edges = this.props.graph.edges;

        return (
            <div className="App-info-panel">
                <ElementsCounter entityName={"nodes"} count={nodes.length}/>
                <ElementsCounter entityName={"edges"} count={edges.length}/>
                <div className="App-line-split"/>
                <ElementDisplay element={this.props.selectedElement}/>

            </div>
        );
    }
}

export default InfoPanel;
