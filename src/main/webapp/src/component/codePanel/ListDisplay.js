import React from "react";
import EditableView, {
    getHighlightedEdge, getHighlightedNodes,
    getSelectedEdge,
    getSelectedNodes,
    highlight,
    select,
    unhighlight
} from "./EditableView";
import HighlighableTd from "./HighlighableTd";
import {
    ABSENT_DIRECTION,
    BOTH_DIRECTIONS,
    FORWARD_DIRECTION,
    SELF_DIRECTION
} from "../drawingArea/DrawingModeConstants";

export class ListDisplay extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            highlightRow: -1,
            highlightColumn: -1
        }
    }

    graphToCode = () => {
        let graph = this.props.graph;
        const n = graph.nodes.length;

        let code = [];
        [...Array(n).keys()].forEach(i => {
            code.push([]);
        });

        //filer placeholder absent edges
        let edges = graph.edges.slice().filter(e => e.direction !== ABSENT_DIRECTION);

        edges.forEach(e => {
            code[e.fromId].push(e.toId);
        });
        return code;
    }

    codeToGraph = () => {
        let nodes = [];
        let edges = [];
        let nextEdgeId = 0;

        let code = document.getElementById("textarea_list").value;
        code = code.trim();

        //calculate graph size
        let list = code.split("\n");
        const n = list.length;

        //create nodes
        [...Array(n).keys()].forEach(i => {
            nodes.push({id: i});
        });

        //parse a list
        for (let i = 0; i < n; i++) {
            let line = list[i].trim().split(":");

            //validate from node
            const fromNode = parseInt(line[0]);
            if (isNaN(fromNode) || fromNode !== i) throw "From node is not valid";

            //validate edges
            if (line[1].trim() === "") {
                list[i] = []; continue;
            }
            let elements = line[1].trim().split(",");
            if (elements.length >= n) throw "Too many edges from node " + i;
            if (new Set(elements).size !== elements.length) throw "Duplicate to nodes not allowed";
            for (let j = 0; j < elements.length; j++) {
                elements[j] = parseInt(elements[j]);
                if (isNaN(elements[j])) throw "To node is not a number";
                list[i] = elements;
            }
        }

        //create edges
        for (let k = 0; k < n; k++) {
            for (let l = 0; l < list[k].length; l++) {
                let toNode = list[k][l];
                edges.push({
                    id: nextEdgeId++,
                    fromId: k,
                    toId: toNode,
                    weight: 1
                });
            }
        }

        let pairs = [];
        edges.forEach(e => {
            if (e.pairId === undefined) {
                if (e.fromId === e.toId) {
                    e.pairId = e.id;
                    e.direction = SELF_DIRECTION;
                    return;
                }
                let pair = edges.find(ee => ee.fromId === e.toId && ee.toId === e.fromId);
                if (pair === undefined) {
                    pair = {
                        id: nextEdgeId++,
                        pairId: e.id,
                        fromId: e.toId,
                        toId: e.fromId,
                        weight: 0,
                        direction: ABSENT_DIRECTION
                    }
                    pairs.push(pair);
                    e.pairId = pair.id;
                    e.direction = FORWARD_DIRECTION;
                } else {
                    e.direction = BOTH_DIRECTIONS;
                    pair.direction = BOTH_DIRECTIONS;
                    e.pairId = pair.id;
                    pair.pairId = e.id;
                }
            }
        })
        edges.push(...pairs);

        this.props.handleGraphUpdate({
            nodes: nodes,
            edges: edges
        });
    }

    renderTable = () => {
        let code = this.graphToCode();
        let selectedEdges = getSelectedEdge(this.props.graph);
        let selectedNodes = getSelectedNodes(this.props.graph);
        let highlightedEdges = getHighlightedEdge(this.props.graph);
        let highlightedNodes = getHighlightedNodes(this.props.graph);

        return (
            <div className="Code-panel-table">
                <table>
                    <tbody>
                    {code.map((node, i) => {
                        return (
                            <tr>
                                <th className="Code-panel-theader" scope="row">{i}</th>
                                {node.map((el, j) => {
                                    return <HighlighableTd
                                        value={el}
                                        focused={this.state.highlightRow === i && this.state.highlightColumn === j
                                            || selectedEdges.find(s => s === i + ":" + el) !== undefined
                                            || highlightedEdges.find(s => s === i + ":" + el) !== undefined}
                                        halfFocused={selectedNodes.find(s => s === i) !== undefined ||
                                            highlightedNodes.find(s => s === i) !== undefined}

                                        highlight={() => {
                                            highlight(this.props.graph, i, el);
                                            this.props.handleGraphUpdate(this.props.graph);
                                        }}
                                        unhighlight={() => {
                                            unhighlight(this.props.graph);
                                            this.props.handleGraphUpdate(this.props.graph);
                                        }}
                                        select={() => {
                                            select(this.props.graph, i, el);
                                            this.props.handleGraphUpdate(this.props.graph);
                                        }}
                                    />
                                })}
                            </tr>)
                    })}
                    </tbody>
                </table>
            </div>
        )
    }

    render() {
        let code = this.graphToCode();

        let newCode;
        if (code !== undefined) {
            newCode = [];
            code.forEach((line, i) => {
                newCode.push(i + " : " + line);
            })
        }

        return <EditableView
            renderTable={this.renderTable}
            label="Adjacency List"
            code={newCode}
            updateGraph={this.codeToGraph}
            textareaId="textarea_list"
        />
    }

}

export default ListDisplay;