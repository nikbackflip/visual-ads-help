import React from "react";
import {
    BOTH_DIRECTIONS,
    FORWARD_DIRECTION,
    NO_DIRECTIONS,
    REVERSE_DIRECTION
} from "../drawingArea/DrawingModeConstants";
import EditableView, {fixIds, highlight, select, unhighlight} from "./EditableView";
import HighlighableTd from "./HighlighableTd";

export class ListDisplay extends React.Component {

    idMap = {}

    constructor(props) {
        super(props);
        this.state = {
            highlightRow: -1,
            highlightColumn: -1
        }
    }

    graphToCode = () => {
        let graph = fixIds(this.props.graph);
        this.idMap = graph.map;

        const n = graph.nodes.length;

        let code = [];
        [...Array(n).keys()].forEach(i => {
            code.push([]);
        });

        graph.edges.forEach(e => {
            switch (e.direction) {
                case NO_DIRECTIONS:
                case BOTH_DIRECTIONS:
                    code[e.fromId].push(e.toId);
                    code[e.toId].push(e.fromId);
                    break;
                case FORWARD_DIRECTION:
                    code[e.fromId].push(e.toId);
                    break;
                case REVERSE_DIRECTION:
                    code[e.toId].push(e.fromId);
                    break;
            }
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
                if (elements[j] === fromNode)  throw "Self nodes not allowed";
                list[i] = elements;
            }
        }

        //create edges
        for (let k = 0; k < n; k++) {
            for (let l = 0; l < list[k].length; l++) {
                let toNode = list[k][l];
                let direction = FORWARD_DIRECTION;
                if (list[toNode].includes(k)) direction = BOTH_DIRECTIONS;
                edges.push({
                    id: nextEdgeId++,
                    fromId: k,
                    toId: toNode,
                    direction: direction,
                    weight: 1
                });
            }
        }

        //remove duplicate double edges
        for (let e = 0; e < edges.length; e++) {
            if (edges[e].direction === BOTH_DIRECTIONS) {
                const from = edges[e].fromId;
                const to = edges[e].toId;
                let index = edges.findIndex(ed => {
                    return (ed.direction === BOTH_DIRECTIONS) && (ed.fromId === to) && (ed.toId === from);
                });
                edges.splice(index, 1);
            }
        }

        this.props.handleGraphUpdate({
            nodes:nodes,
            edges:edges
        });
    }

    renderTable = () => {
        let code = this.graphToCode();
        let selectedEdges = this.getSelectedElements();
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
                                        halfHighlighted={false}
                                        highlighted={this.state.highlightRow === i && this.state.highlightColumn === j}
                                        selected={selectedEdges.find(s => s === i + ":" + el) !== undefined}
                                        highlight={() => {
                                            highlight(this.props.graph, this.idMap, i, el);
                                            this.highlight(i, j);
                                            this.props.handleGraphUpdate(this.props.graph);
                                        }}
                                        unhighlight={() => {
                                            unhighlight(this.props.graph);
                                            this.highlight(-1, -1);
                                            this.props.handleGraphUpdate(this.props.graph);
                                        }}
                                        select={() => {
                                            select(this.props.graph, this.idMap, i, el);
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

    getSelectedElements = () => {
        let selected = [];
        this.props.graph.edges.forEach(e => {
            if (e.selected) {
                switch (e.direction) {
                    case NO_DIRECTIONS:
                    case BOTH_DIRECTIONS:
                        selected.push(e.fromId + ":" + e.toId);
                        selected.push(e.toId + ":" + e.fromId);
                        break;
                    case FORWARD_DIRECTION:
                        selected.push(e.fromId + ":" + e.toId);
                        break;
                    case REVERSE_DIRECTION:
                        selected.push(e.toId + ":" + e.fromId);
                        break;
                }
            }
        })
        return selected;
    }

    highlight = (i, j) => {
        this.setState({
            highlightRow: i,
            highlightColumn: j
        })
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