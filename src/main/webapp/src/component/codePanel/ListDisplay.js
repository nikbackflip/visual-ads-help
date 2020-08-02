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
        [...Array(n).keys()].forEach(() => {
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

        //read text area
        let textInput = document.getElementById("textarea_list").value.trim();

        //parse text input to list
        const textLines = textInput.split("\n");
        const n = textLines.length;
        let list = {};
        for (let i = 0; i < n; i++) {
            const line = textLines[i].trim().split(":");

            //validate from node
            const fromNode = parseInt(line[0]);
            if (isNaN(fromNode) || fromNode !== i) throw "From node is not valid";

            //validate to list
            const validatedToNodes = [];
            if (line[1].trim() !== "" ) {
                const toList = line[1].trim().split(",");
                if (toList.length > n) throw "Too many edges from node " + i; //TODO maybe obsolete check
                if (new Set(toList).size !== toList.length) throw "Duplicate to nodes not allowed";
                toList.forEach(elem => {
                    const toNode = parseInt(elem.trim());
                    if (isNaN(toNode) || toNode < 0 || toNode >= n) throw "To node is not a number or out of range";
                    validatedToNodes.push(toNode);
                });
            }

            //line is valid
            list[fromNode] = validatedToNodes;
        }

        //create nodes
        [...Array(n).keys()].forEach(i => {
            nodes.push({id: i});
        });

        //create edges
        for (let i = 0; i < n; i++) {
            list[i].forEach(toNode => {
                edges.push({
                    id: nextEdgeId++,
                    fromId: i,
                    toId: toNode,
                    weight: 1
                });
            })
        }

        //set edge directions and create placeholder absent edges
        let pairs = [];
        edges.forEach(e => {
            if (e.pairId === undefined) {
                if (e.fromId === e.toId) {
                    e.pairId = e.id;
                    e.direction = SELF_DIRECTION;
                    return;
                }
                let pair = edges.find(p => p.fromId === e.toId && p.toId === e.fromId);
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

        //submit graph
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