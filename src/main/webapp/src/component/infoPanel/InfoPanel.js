import React from 'react';
import "../css/InfoPanel.css"
import ElementsCounter from "./ElementsCounter";
import DisplayEdge from "./DisplayEdge";
import {ABSENT_DIRECTION, SELF_DIRECTION} from "../drawingArea/DrawingModeConstants";
import GraphAnalytics from "./GraphAnalytics";

class InfoPanel extends React.Component {

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

    updateTwoEdges = (firstEdge, secondEdge) => {
        let edges = this.props.graph.edges.slice();
        const indexEdge = edges.findIndex(n => {
            return n.id === firstEdge.id
        });
        edges[indexEdge] = firstEdge;

        const indexPair = edges.findIndex(n => {
            return n.id === secondEdge.id
        });
        edges[indexPair] = secondEdge;

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

        const selectedEdges = edges.filter(e => {
            return e.selected === true;
        });

        let excludedEdges = [];

        let totalEdges = edges.slice().filter(e => e.direction !== ABSENT_DIRECTION).length;
        if (!this.props.config.graphDirectional) {
            const selfEdges = edges.slice().filter(e => e.direction === SELF_DIRECTION).length
            totalEdges = (totalEdges - selfEdges) / 2 + selfEdges;
        }

        return (
            <div className="App-info-panel">
                <ElementsCounter entityName={"nodes"} count={nodes.length}/>
                <ElementsCounter entityName={"edges"} count={totalEdges}/>
                <div className="App-line-split"/>
                <div>
                    <GraphAnalytics
                        config={this.props.config}
                        graph={this.props.graph}
                    />
                </div>
                <div className="Info-panel-scroll Info-panel-edges">
                    {selectedEdges.map(e => {
                        if (e.direction === ABSENT_DIRECTION) return null;
                        if (e.direction !== SELF_DIRECTION) excludedEdges.push(e.pairId);
                        return excludedEdges.find(ex => ex === e.id) ? null :
                            <DisplayEdge
                                key={e.id}
                                element={e}
                                config={this.props.config}
                                pair={edges.find(p => p.id === e.pairId)}
                                getNodeName={this.getNodeName}
                                updateElement={this.updateEdge}
                                updateBoth={this.updateTwoEdges}
                            />
                    })}
                </div>
            </div>
        );
    }
}

export default InfoPanel;
