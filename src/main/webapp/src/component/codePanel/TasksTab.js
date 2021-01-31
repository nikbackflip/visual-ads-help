import React from 'react';
import {ABSENT_DIRECTION} from "../drawingArea/DrawingModeConstants";

class TasksDropdown extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            params: {},
            response: {}
        };
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
        if (JSON.stringify(prevProps.tasks) !== JSON.stringify(this.props.tasks)) {
            this.setState({
                selectedTask: this.props.tasks[0]
            })
        }
    }

    onTaskChange = (e) => {
        const selectedTask = this.props.tasks.find(t => t.id === e.target.value);
        this.setState({
            selectedTask: selectedTask,
            params: {}
        });
    }

    onInputEnd = (key, value) => {
        let values = Object.assign({}, this.state);
        this.state.params[key] = value;
        this.setState(values);
    }

    getValues = () => {
        let values = {};
        this.state.selectedTask.parameters.slice().forEach(p => {
            values.push({
                id: p.id,
                value: this.state[p.id] === undefined ? null : this.state[p.id]
            })
        });
        return values;
    }

    executeTask = () => {
        let graph = {
            edges: this.props.graph.edges.slice().filter(e => e.direction !== ABSENT_DIRECTION),
            nodes: this.props.graph.nodes
        }
        let self = this;
        fetch('/tasks/' + this.state.selectedTask.id, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                graph: {
                    nodes: graph.nodes,
                    edges: graph.edges,
                    config: this.props.config
                },
                params: this.state.params
            })
        }).then((response) => {
            return response.json().then(json => ({status: response.status, json}))
        }).then((data) => {

            if (data.status === 400) {
                self.setState({
                    response: {
                        success: false,
                        message: data.json.message
                    }
                });
                return;
            }

            self.props.graph.nodes.forEach(n => n.selected = false);
            self.props.graph.edges.forEach(n => n.selected = false);

            if (data.json.nodes.length !== 0) {
                self.props.graph.nodes.filter(n => {
                    return data.json.nodes.find(nn => n.id === nn)
                }).forEach((n) => n.selected = true);
            }

            if (data.json.edges.length !== 0) {
                self.props.graph.edges.filter(e => {
                    return data.json.edges.find(ee => e.fromId === ee.from && e.toId === ee.to)
                }).forEach((e) => e.selected = true);
            }

            self.setState({
                response: {
                    success: true,
                    edges: data.json.edges,
                    nodes: data.json.nodes,
                    components: data.json.components
                }
            });
            self.props.handleGraphUpdate(self.props.graph);
        });
    }


    render() {
        const inputFields = this.state.selectedTask ?
            this.state.selectedTask.parameters.map(p => {
                return (
                    <div
                        key={this.state.selectedTask.id + ":" + p.id}
                        className="Code-panel-tasks-parameter-div"
                    >
                        <label className="Code-panel-tasks-parameter-label">{p.label}: </label>
                        <span className="Code-panel-tasks-parameter-span">
                            <input
                                className="Code-panel-tasks-parameter-input"
                                type="number"
                                onBlur={(e) => this.onInputEnd(p.id, e.target.value)}
                            />
                        </span>
                    </div>
                )
            }) : null;

        return (
            <div className="Code-panel-whole-height">
                <div className="Code-panel-tasks-up">
                    <div className="Code-panel-tasks-input">
                        <select
                            className="Code-panel-tasks-dropdown"
                            onChange={this.onTaskChange}
                            value={this.state.selectedTask ? this.state.selectedTask.id : undefined}
                        >
                            {this.props.tasks.map(t => {
                                return (
                                    <option
                                        key={t.id}
                                        value={t.id}
                                    >
                                        {t.label}
                                    </option>)
                            })}
                        </select>
                        <div>
                            {inputFields}
                        </div>
                    </div>
                    <div className="Code-panel-tasks-exec">
                        <button
                            className="Control-panel-button Code-panel-tasks-exec-button"
                            onClick={this.executeTask}
                        >
                            EXECUTE
                        </button>
                    </div>
                </div>
                <div className="Code-panel-tasks-bottom Code-panel-task-response">
                    {
                        !this.state.response.success ? this.state.response.message :
                            <div>
                                <NodesResponse
                                    nodes={this.state.response.nodes}
                                />
                                <EdgesResponse
                                    edges={this.state.response.edges}
                                />
                                <ComponentsResponse
                                    components={this.state.response.components}
                                />
                            </div>
                    }
                </div>
            </div>
        )
    }
}

class NodesResponse extends React.Component {
    render() {
        if (this.props.nodes.length === 0) return <div />
        return <div>
            Nodes:
            {
                this.props.nodes.join(", ")
            }
        </div>
    }
}
class EdgesResponse extends React.Component {
    render() {
        if (this.props.edges.length === 0) return <div />
        return <div>
            Edges:
            <br />
            {
                this.props.edges.map(e => e.from + " -> " + e.to).join(", ")
            }
        </div>
    }
}
class ComponentsResponse extends React.Component {
    render() {
        if (this.props.components.length === 0) return <div />
        return <div>
            Components:
            <br />
            {
                this.props.components.map(c => c.join(", ")).join("\n")
            }
        </div>
    }
}

class TasksTab extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            tasks: []
        };
    }

    componentDidMount() {
        this.fetchAvailableTasks();
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
        if (JSON.stringify(prevProps) !== JSON.stringify(this.props)) {
            this.fetchAvailableTasks();
        }
    }

    fetchAvailableTasks = () => {
        let graph = {
            edges: this.props.graph.edges.slice().filter(e => e.direction !== ABSENT_DIRECTION),
            nodes: this.props.graph.nodes
        }
        let self = this;

        fetch('/tasks', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                nodes: graph.nodes,
                edges: graph.edges,
                config: this.props.config
            })
        }).then((response) => {
            return response.json();
        }).then((data) => {
            self.setState({
                tasks: data.availableTasks
            });
        });
    }

    render() {
        return (
            <TasksDropdown
                tasks={this.state.tasks}
                graph={this.props.graph}
                config={this.props.config}
                handleGraphUpdate={this.props.handleGraphUpdate}
            />
        )
    }

}

export default TasksTab;
