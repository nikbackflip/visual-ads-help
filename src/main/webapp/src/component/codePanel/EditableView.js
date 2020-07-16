import React from "react";
import ViewControlPanel from "./ViewControlPanel";

export function fixIds(graph) {
    let nodes = [];
    let edges = [];

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
    }
    return {
        nodes: nodes,
        edges: edges
    }
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