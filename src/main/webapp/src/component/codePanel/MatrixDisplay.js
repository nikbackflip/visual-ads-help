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
import { colors } from "../drawingArea/DrawingModeConstants";
import {internalGraphToMatrix, matrixToInternalGraph} from "../../util/GraphTransformationUtil"


export class MatrixDisplay extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            highlightRow: -1,
            highlightColumn: -1
        }
    }

    codeToGraph = () => {
        //read text area
        let textInput = document.getElementById("textarea_matrix").value.trim();

        //parse text input to matrix
        const textLines = textInput.split("\n");
        const n = textLines.length;
        let matrix = {};
        for (let i = 0; i < n; i++) {
            const validatedToNodes = [];
            let elements = textLines[i].split(",");
            if (elements.length !== n) throw "Matrix is not a square.";
            for (let j = 0; j < n; j++) {
                const value = parseFloat(elements[j]);
                if (!this.props.config.selfLoopsAllowed &&  i === j && value !== 0) throw "Graph is configured to forbid self loops.";
                if (isNaN(value)) throw "Matrix values are not valid.";
                if (!(this.props.config.graphWeighted || (value === 0 || value === 1))) throw "Graph is configured to be unweighted.";
                validatedToNodes.push(value);
            }
            matrix[i] = validatedToNodes;
        }
        if (!this.props.config.graphDirectional) {
            for (let i = 0; i < n; i++) {
                for (let j = 0; j < n; j++) {
                    if (matrix[i][j] !== matrix[j][i]) throw "Graph is configured to be undirectional.";
                }
            }
        }

        let generatedGraph = matrixToInternalGraph(matrix, this.props.config);
        this.layout(generatedGraph);
    }

    layout = (graph) => {
        let self = this;
        fetch("/layout" + '?x=' + Math.floor(this.props.stage.width) + '&y=' + Math.floor(this.props.stage.height), {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({
                nodes: graph.nodes,
                edges: graph.edges,
                config: this.props.config
            })
        }).then((response) => {
            return response.json();
        }).then((data) => {
            console.log(JSON.stringify(data));
            let nodes = graph.nodes;
            let coordinates = data.coordinates;

            if (coordinates !== null && coordinates !== undefined) {
                console.log("setting coords");
                nodes.forEach(i => {
                    i.x = coordinates[i.id].x;
                    i.y = coordinates[i.id].y;
                    i.name = i.id;
                    i.color = colors.sample();
                    i.selected = false;
                    i.highlighted = false;
                });
            }
            self.props.handleGraphUpdate(graph);
        });
    }

    renderTable = () => {
        let code = internalGraphToMatrix(this.props.graph);
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
        let code = internalGraphToMatrix(this.props.graph);

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
