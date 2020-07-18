import React from "react";
import ViewControlPanel from "./ViewControlPanel";

export function fixIds(graph) {
    let nodes = [];
    let edges = [];
    let idMap = {};

    graph.nodes.forEach(n => {
        nodes.push({
            id: n.id,
            name: n.name
        })
    });

    graph.edges.forEach(e => {
        edges.push({
            id: e.id,
            fromId: e.fromId,
            toId: e.toId,
            weight: e.weight,
            direction: e.direction
        })
    });

    nodes.sort(function(a, b) {
        if (a.id > b.id) return 1;
        if (a.id < b.id) return -1;
        return 0;
    });

    let n = nodes.length;
    for (let i = 0; i < n; i++) {
        let node = nodes[i];
        let oldId = node.id;
        edges.forEach(e => {
            if (e.fromId === oldId) e.fromId = i;
            if (e.toId === oldId) e.toId = i;
        })
        node.id = i;
        idMap[i] = oldId;
    }
    return {
        nodes: nodes,
        edges: edges,
        map: idMap
    }
}

export function highlight (graph, idMap, from, to) {
    graph.nodes.forEach(n => n.highlighted = false);
    graph.edges.forEach(n => n.highlighted = false);

    graph.nodes.filter(n => {
        return n.id === idMap[to] || n.id === idMap[from]
    }).forEach(n => n.highlighted=true);
    graph.edges.filter(e => {
        return e.fromId === idMap[from] && e.toId === idMap[to]
            || e.fromId === idMap[to] && e.toId === idMap[from]
    }).forEach(e => e.highlighted=true);
}

export function unhighlight (graph) {
    graph.nodes.forEach(n => n.highlighted = false);
    graph.edges.forEach(n => n.highlighted = false);
}

export function select (graph, idMap, from, to) {
    graph.nodes.forEach(n => n.selected = false);
    graph.edges.forEach(n => n.selected = false);

    graph.nodes.filter(n => {
        return n.id === idMap[to] || n.id === idMap[from]
    }).forEach((n) => n.selected=true);

    graph.edges.filter(e => {
        return e.fromId === idMap[from] && e.toId === idMap[to]
            || e.fromId === idMap[to] && e.toId === idMap[from]
    }).forEach((n) => n.selected=true);
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
                />
                {view}
            </div>
        )
    }
}

export default EditableView;