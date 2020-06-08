import React from 'react';
import "../css/InfoPanel.css"
import ElementsCounter from "./ElementsCounter";
import DisplayNode from "./DisplayNode";
import DisplayEdge from "./DisplayEdge";

class InfoPanel extends React.Component {

    render() {
        console.log("Rendering info panel");

        const nodes = this.props.graph.nodes;
        const edges = this.props.graph.edges;
        const selected = this.props.selectedElement;


        const selectedNode = selected.nodeId == null ? {} :
            nodes.find(n => {
                return n.id === selected.nodeId;
            });

        const selectedEdge = selected.edgeId == null ? {} :
            edges.find(n => {
                return n.id === selected.edgeId;
            });


        return (
            <div className="App-info-panel">
                <ElementsCounter entityName={"nodes"} count={nodes.length}/>
                <ElementsCounter entityName={"edges"} count={edges.length}/>
                <div className="App-line-split"/>
                <DisplayNode element={selectedNode}/>
                <div className="App-line-split"/>
                <DisplayEdge element={selectedEdge}/>
            </div>
        );
    }
}

export default InfoPanel;
