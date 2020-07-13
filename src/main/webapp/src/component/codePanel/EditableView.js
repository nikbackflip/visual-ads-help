import React from "react";
import ViewControlPanel from "./ViewControlPanel";


export class MatrixDisplay extends React.Component {

    renderTable = () => {
        let header = null
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
        />
    }

}

export class ListDisplay extends React.Component {

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
        return <EditableView
                renderTable={this.renderTable}
                label="Adjacency List"
                code={this.props.code}
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

    renderTextarea = () => {
        return <textarea
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
                />
                {view}
            </div>
        )
    }
}
