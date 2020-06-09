import React from 'react';
import "../css/InfoPanel.css"
import ElementsCounter from "./ElementsCounter";
import DisplayNode from "./DisplayNode";
import DisplayEdge from "./DisplayEdge";

class InfoPanel extends React.Component {

    updateNode = (node) => {
        let nodes = this.props.graph.nodes.slice();
        const index = nodes.findIndex(n => {
            return n.id === node.id
        });
        nodes[index] = node;

        this.props.handleGraphUpdate({
            nodes: nodes,
            edges: this.props.graph.edges.slice()
        });
    }

    getSelectedNodeDisplay = () => {
        const selectedNode = this.props.graph.nodes.find(n => {
            return n.selected === true;
        });
        let renderedNode = null;
        if (selectedNode != null) {
            renderedNode = <DisplayNode
                element={selectedNode}
                updateElement={this.updateNode}
            />
        }
        return renderedNode;
    }

    getSelectedEdgeDisplay = () => {
        const selectedEdge = this.props.graph.edges.find(e => {
            return e.selected === true;
        });
        let renderedEdge = null;
        if (selectedEdge != null) {
            renderedEdge = <DisplayEdge element={selectedEdge}/>
        }
        return renderedEdge;
    }

    render() {
        console.log("Rendering info panel");

        const nodes = this.props.graph.nodes;
        const edges = this.props.graph.edges;

        return (
            <div className="App-info-panel">
                <ElementsCounter entityName={"nodes"} count={nodes.length}/>
                <ElementsCounter entityName={"edges"} count={edges.length}/>
                <div className="App-line-split"/>
                {this.getSelectedNodeDisplay()}
                {this.getSelectedEdgeDisplay()}
            </div>
        );
    }
}

export default InfoPanel;
