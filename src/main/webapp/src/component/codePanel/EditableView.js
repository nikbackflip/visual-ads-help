import React from "react";
import ViewControlPanel from "./ViewControlPanel";
import {BOTH_DIRECTIONS, FORWARD_DIRECTION} from "../drawingArea/DrawingModeConstants";


export class MatrixDisplay extends React.Component {

    updateGraph = () => {
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
        let header = null;
        let body = null;
        const n = Object.keys(this.props.code).length;
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
                {this.props.code.map((node, i) => {
                    return (
                        <tr>
                            <th className="Code-panel-theader" scope="row">{i}</th>
                            {node.map(el => {
                                let element = el;
                                if (el === null) {
                                    element = " ";
                                }
                                return <td  className="Code-panel-tbody">{element}</td>
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
        return <EditableView
            renderTable={this.renderTable}
            label="Adjacency Matrix"
            code={this.props.code}
            updateGraph={this.updateGraph}
            textareaId="textarea_matrix"
        />
    }

}

export class ListDisplay extends React.Component {

    updateGraph = () => {
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
        return (
            <div className="Code-panel-table">
                <table>
                    <tbody>
                        {this.props.code.map((node, i) => {
                            return (
                                <tr>
                                    <th className="Code-panel-theader" scope="row">{i}</th>
                                    {node.map(el => {
                                        return <td  className="Code-panel-tbody">{el}</td>
                                    })}
                                </tr>)
                        })}
                    </tbody>
                </table>
            </div>
        )
    }

    render() {
        let code;
        if (this.props.code !== undefined) {
            code = [];
            this.props.code.forEach((line, i) => {
                code.push(i + " : " + line);
            })
        }

        return <EditableView
                renderTable={this.renderTable}
                label="Adjacency List"
                code={code}
                updateGraph={this.updateGraph}
                textareaId="textarea_list"
            />
    }

}

class EditableView extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            editMode: false
        }
    }

    switchToTextArea = () => {
        this.setState({
            editMode: true
        })
    }

    cancelEdit = () => {
        this.setState({
            editMode: false
        })
    }

    updateGraph = () => {
        this.setState({
            editMode: false
        });
        this.props.updateGraph();
    }

    renderTextarea = () => {
        return <textarea
            id={this.props.textareaId}
            className="Code-panel-textarea"
            defaultValue={this.props.code.join("\n")}
        />
    }

    render() {
        let view;

        if (this.props.code !== undefined) {
            if (this.state.editMode) {
                view = this.renderTextarea();
            } else {
                view = this.props.renderTable();
            }
        }

        return (
            <div className="Code-panel-whole-height">
                <ViewControlPanel
                    header={this.props.label}
                    initEdit={this.switchToTextArea}
                    cancelEdit={this.cancelEdit}
                    updateGraph={this.updateGraph}
                />
                {view}
            </div>
        )
    }
}
