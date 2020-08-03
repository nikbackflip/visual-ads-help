import React from "react";
import ViewControlPanel from "./ViewControlPanel";


export function highlight (graph, from, to) {
    graph.nodes.forEach(n => n.highlighted = false);
    graph.edges.forEach(n => n.highlighted = false);

    graph.nodes.filter(n => {
        return n.id === to || n.id === from
    }).forEach(n => n.highlighted=true);
    graph.edges.filter(e => {
        return e.fromId === from && e.toId === to
            || e.fromId === to && e.toId === from
    }).forEach(e => e.highlighted=true);
}

export function unhighlight (graph) {
    graph.nodes.forEach(n => n.highlighted = false);
    graph.edges.forEach(n => n.highlighted = false);
}

export function select (graph, from, to) {
    graph.nodes.forEach(n => n.selected = false);
    graph.edges.forEach(n => n.selected = false);

    graph.nodes.filter(n => {
        return n.id === to || n.id ===from
    }).forEach((n) => n.selected=true);

    graph.edges.filter(e => {
        return e.fromId === from && e.toId === to
            || e.fromId === to && e.toId === from
    }).forEach((n) => n.selected=true);
}

export function getSelectedNodes(graph) {
    let selected = [];
    graph.nodes.filter(n => n.selected)
        .forEach(n => {
            selected.push(n.id);
        });
    return selected;
}

export function getSelectedEdge(graph) {
    let selected = [];
    graph.edges.forEach(e => {
        if (e.selected) {
            selected.push(e.fromId + ":" + e.toId);
        }
    })
    return selected;
}

export function getHighlightedNodes(graph) {
    let selected = [];
    graph.nodes.filter(n => n.highlighted)
        .forEach(n => {
            selected.push(n.id);
        });
    return selected;
}

export function getHighlightedEdge(graph) {
    let selected = [];
    graph.edges.forEach(e => {
        if (e.highlighted) {
            selected.push(e.fromId + ":" + e.toId);
        }
    })
    return selected;
}

class EditableView extends React.Component {
    buttons = React.createRef();

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
        try {
            this.props.updateGraph();
        } catch (e) {
            this.buttons.current.flash();
            return;
        }
        this.setState({
            editMode: false
        });
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
                    ref={this.buttons}
                    header={this.props.label}
                    initEdit={this.switchToTextArea}
                    cancelEdit={this.cancelEdit}
                    updateGraph={this.updateGraph}
                    editMode={this.state.editMode}
                    help={this.props.help}
                />
                {view}
            </div>
        )
    }
}

export default EditableView;