import React from "react";
import {
    BOTH_DIRECTIONS,
    FORWARD_DIRECTION,
    NO_DIRECTIONS,
    REVERSE_DIRECTION
} from "../drawingArea/DrawingModeConstants";
import EditableView, {highlight, select, unhighlight} from "./EditableView";
import HighlighableTd from "./HighlighableTd";


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

        //set weights
        graph.edges.forEach(e => {
            switch (e.direction) {
                case NO_DIRECTIONS:
                case BOTH_DIRECTIONS:
                    code[e.fromId][e.toId] = e.weight;
                    code[e.toId][e.fromId] = e.weight;
                    break;
                case FORWARD_DIRECTION:
                    code[e.fromId][e.toId] = e.weight;
                    break;
                case REVERSE_DIRECTION:
                    code[e.toId][e.fromId] = e.weight;
                    break;
            }
        })

        return code;
    }

    codeToGraph = () => {
        let nodes = [];
        let edges = [];
        let nextEdgeId = 0;

        let code = document.getElementById("textarea_matrix").value;
        code = code.trim();

        //calculate graph size
        let matrix = code.split("\n");
        const n = matrix.length;

        //create nodes
        [...Array(n).keys()].forEach(i => {
            nodes.push({id: i});
        });

        //parse a matrix
        for (let i = 0; i < n; i++) {
            let elements = matrix[i].split(",");
            if (elements.length !== n) throw "Matrix is not a square";
            for (let j = 0; j < n; j++) {
                elements[j] = parseFloat(elements[j]);
                if (isNaN(elements[j])) throw "Matrix values not valid";
            }
            matrix[i] = elements;
        }

        //create edges
        for (let k = 0; k < n; k++) {
            if (matrix[k][k] !== 0) throw "Self edges can only be zero"
            for (let l = 0; l < n; l++) {
                const weight = matrix[k][l];
                if (weight !== 0) {
                    let direction = FORWARD_DIRECTION;
                    if (matrix[l][k] !== 0 && matrix[l][k] !== weight) throw "Different weights on different directions not allowed";
                    if (matrix[l][k] === weight) direction = BOTH_DIRECTIONS;
                    edges.push({
                        id: nextEdgeId++,
                        fromId: k,
                        toId: l,
                        weight: weight,
                        direction: direction
                    });
                }
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
        let selected = this.getSelectedElements();

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
                                halfHighlighted={this.state.highlightRow === i || this.state.highlightColumn === j}
                                highlighted={this.state.highlightRow === i && this.state.highlightColumn === j}
                                selected={selected.find(s => s === i + ":" + j) !== undefined}
                                highlight={() => {
                                    highlight(this.props.graph, i, j);
                                    this.highlight(i, j);
                                    this.props.handleGraphUpdate(this.props.graph);
                                }}
                                unhighlight={() => {
                                    unhighlight(this.props.graph)
                                    this.highlight(-1, -1);
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

        return <EditableView
            renderTable={this.renderTable}
            label="Adjacency Matrix"
            code={code}
            updateGraph={this.codeToGraph}
            textareaId="textarea_matrix"
        />
    }

}

export default MatrixDisplay;