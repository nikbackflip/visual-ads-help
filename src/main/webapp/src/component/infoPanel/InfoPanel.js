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

    updateEdge = (edge) => {
        let edges = this.props.graph.edges.slice();
        const index = edges.findIndex(n => {
            return n.id === edge.id
        });
        edges[index] = edge;

        this.props.handleGraphUpdate({
            nodes: this.props.graph.nodes.slice(),
            edges: edges
        });
    }

    getNodeName = (id) => {
        return this.props.graph.nodes.find(n => {
            return n.id === id
        });
    }

    render() {
        const nodes = this.props.graph.nodes;
        const edges = this.props.graph.edges;

        const selectedNodes = nodes.filter(n => {
            return n.selected === true;
        });
        const selectedEdges = edges.filter(e => {
            return e.selected === true;
        });

        return (
            <div className="App-info-panel">
                <ElementsCounter entityName={"nodes"} count={nodes.length}/>
                <ElementsCounter entityName={"edges"} count={edges.length}/>
                <div className="App-line-split"/>
                {selectedNodes.map(n => {
                    return <DisplayNode
                        key={n.id}
                        element={n}
                        updateElement={this.updateNode}
                    />
                })}
                {selectedEdges.map(e => {
                    return <DisplayEdge
                        key={e.id}
                        element={e}
                        getNodeName={this.getNodeName}
                        updateElement={this.updateEdge}
                    />
                })}
            </div>
        );
    }
}

export default InfoPanel;
