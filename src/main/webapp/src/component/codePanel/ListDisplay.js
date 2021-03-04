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
import { internalGraphToList, listToInternalGraph } from "../../util/GraphTransformationUtil"
import layout from "../../util/LayoutUtil";

export class ListDisplay extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            highlightRow: -1,
            highlightColumn: -1
        }
    }

    codeToGraph = () => {
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
            if (isNaN(fromNode) || fromNode !== i) throw "From node is not valid.";

            //validate to list
            const validatedToNodes = [];
            if (line[1].trim() !== "" ) {
                const toList = line[1].trim().split(",");
                if (toList.length > n) throw "Too many edges from node " + i + "."; //TODO maybe obsolete check
                if (new Set(toList).size !== toList.length) throw "Duplicate to nodes are not allowed.";
                toList.forEach(elem => {
                    const toNode = parseInt(elem.trim());
                    if (isNaN(toNode) || toNode < 0 || toNode >= n) throw "To node is not a number or out of range.";
                    if (!this.props.config.selfLoopsAllowed && i === toNode) throw "Graph is configured to forbid self loops.";
                    validatedToNodes.push(toNode);
                });
            }

            //line is valid
            list[fromNode] = validatedToNodes;
        }
        if (!this.props.config.graphDirectional) {
            for (let i = 0; i < n; i++) {
                list[i].forEach(j => {
                    if (list[j].find(e => e === i) === undefined) throw "Graph is configured to be undirectional.";
                })
            }
        }

        let generatedGraph = listToInternalGraph(list, this.props.config);
        layout(generatedGraph, this.props.config, this.props.stage)
            .then((graphAfterLayout) => {this.props.handleGraphUpdate(graphAfterLayout)});
    }

    renderTable = () => {
        let code = internalGraphToList(this.props.graph);
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
        let code = internalGraphToList(this.props.graph);

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
            help="Input is a n-sized list, each line starts with a from node id, followed by colon and a coma-separated list of connected nodes. Costs ignored, duplicates forbidden. "
        />
    }

}

export default ListDisplay;
