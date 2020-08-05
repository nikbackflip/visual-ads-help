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
    SELF_DIRECTION,
    SPLIT_DIRECTION
} from "../drawingArea/DrawingModeConstants";


export class MatrixDisplay extends React.Component {

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

        //create nxn matrix and fill with 0
        let code = Array.from(Array(n), _ => Array(n).fill(0.0));

        //filer placeholder absent edges
        let edges = graph.edges.slice().filter(e => e.direction !== ABSENT_DIRECTION);

        //set weights
        edges.forEach(e => {
            code[e.fromId][e.toId] = e.weight;
        })
        return code;
    }

    codeToGraph = () => {
        let nodes = [];
        let edges = [];
        let nextEdgeId = 0;

        //read text area
        let textInput = document.getElementById("textarea_matrix").value.trim();

        //parse text input to matrix
        const textLines = textInput.split("\n");
        const n = textLines.length;
        let matrix = {};
        for (let i = 0; i < n; i++) {
            const validatedToNodes = [];
            let elements = textLines[i].split(",");
            if (elements.length !== n) throw "Matrix is not a square";
            for (let j = 0; j < n; j++) {
                const value = parseFloat(elements[j]);
                if (!this.props.config.selfEdgesAllowed &&  i === j && value !== 0) throw "Graph is configured to forbid self edges";
                if (isNaN(value)) throw "Matrix values not valid";
                validatedToNodes.push(value);
            }
            matrix[i] = validatedToNodes;
        }

        //create nodes
        [...Array(n).keys()].forEach(i => {
            nodes.push({id: i});
        });

        //create edges
        for (let i = 0; i < n; i++) {
            matrix[i].forEach((weight, j) => {
                if (weight !== 0) {
                    edges.push({
                        id: nextEdgeId++,
                        fromId: i,
                        toId: j,
                        weight: weight
                    });
                }
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
                    if (e.weight !== pair.weight) {
                        e.direction = SPLIT_DIRECTION;
                        pair.direction = SPLIT_DIRECTION;
                    } else {
                        e.direction = BOTH_DIRECTIONS;
                        pair.direction = BOTH_DIRECTIONS;
                    }
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

        let header = null;
        let body = null;
        const n = Object.keys(code).length;
        header =
            <thead>
            <tr>
                <th />
                {Array.from(Array(n).keys()).map(i => {
                    let headerIndex = i;
                    if (i >= 0 && i < 10) {
                        headerIndex = i + "  ";
                    }
                    return <th className="Code-panel-theader" scope="col">{headerIndex}</th>
                })}
            </tr>
            </thead>

        body =
            <tbody>
            {code.map((node, i) => {
                return (
                    <tr>
                        <th className="Code-panel-theader" scope="row">{i}</th>
                        {node.map((el, j) => {
                            return <HighlighableTd
                                value={el}
                                focused={this.state.highlightRow === i && this.state.highlightColumn === j
                                    || selectedEdges.find(s => s === i + ":" + j) !== undefined
                                    || highlightedEdges.find(s => s === i + ":" + j) !== undefined}
                                halfFocused={selectedNodes.find(s => s === i || s === j) !== undefined
                                    || highlightedNodes.find(s => s === i || s === j) !== undefined}

                                highlight={() => {
                                    highlight(this.props.graph, i, j);
                                    this.props.handleGraphUpdate(this.props.graph);
                                }}
                                unhighlight={() => {
                                    unhighlight(this.props.graph)
                                    this.props.handleGraphUpdate(this.props.graph);
                                }}
                                select={() => {
                                    select(this.props.graph, i, j)
                                    this.props.handleGraphUpdate(this.props.graph);
                                }}
                            />
                        })}
                    </tr>)
            })}
            </tbody>

        return (
            <div className="Code-panel-table">
                <table>
                    {header}
                    {body}
                </table>
            </div>
        );
    }

    render() {
        let code = this.graphToCode();

        return <EditableView
            renderTable={this.renderTable}
            label="Adjacency Matrix"
            code={code}
            updateGraph={this.codeToGraph}
            textareaId="textarea_matrix"
            help="Input is a square matrix, values coma-separated, each value is a corresponding cost, float values allowed."
        />
    }

}

export default MatrixDisplay;