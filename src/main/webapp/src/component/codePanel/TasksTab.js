import React from 'react';
import {ABSENT_DIRECTION} from "../drawingArea/DrawingModeConstants";
import {Box, Button, Chip, Grid, MenuItem, Paper, Select, TextField, Typography} from "@material-ui/core";

class TasksDropdown extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            params: {}
        };
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
        if (JSON.stringify(prevProps.tasks) !== JSON.stringify(this.props.tasks)) {
            this.setState({
                selectedTask: this.props.tasks[0],
            })
        }
        if (JSON.stringify(prevProps.graph) !== JSON.stringify(this.props.graph)) {
            this.setState({
                response: undefined
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
                    },
                    lastTask: self.state.selectedTask.label,
                    lastParams: Object.assign({}, self.state.params)
                });
                return;
            }

            self.props.graph.nodes.forEach(n => n.selected = false);
            self.props.graph.edges.forEach(n => n.selected = false);

            self.setState({
                response: {
                    success: true,
                    edges: data.json.edges,
                    nodes: data.json.nodes,
                    components: data.json.components
                },
                lastTask: self.state.selectedTask.label,
                lastParams: Object.assign({}, self.state.params)
            });
            self.props.handleGraphUpdate(self.props.graph);
        });
    }


    render() {
        const inputFields = this.state.selectedTask ?
            this.state.selectedTask.parameters.map(p => {
                return (
                    <TextField
                        id={this.state.selectedTask.id + ":" + p.id}
                        key={this.state.selectedTask.id + ":" + p.id}
                        label={p.label}
                        onChange={(e) => this.onInputEnd(p.id, e.target.value)}
                        type="number"
                        InputLabelProps={{
                            shrink: true,
                        }}
                    />)}) : null;

        const displayResult = this.state.response !== undefined;

        return (
            <Box m={2}>
                <Paper elevation={5}>
                    <Box overflow="hidden" p={2}>
                        <Grid container direction="row" alignItems="flex-start" justify="space-between" wrap="nowrap">
                            <Grid item>
                                <Select
                                    onChange={this.onTaskChange}
                                    value={this.state.selectedTask ? this.state.selectedTask.id : ""}>
                                        {this.props.tasks.map(t => {
                                            return (
                                                <MenuItem key={t.id} value={t.id}>
                                                    {t.label}
                                                </MenuItem>)
                                        })}
                                </Select>
                            </Grid>
                            <Grid item>
                                <Button variant="contained" onClick={this.executeTask}>
                                    EXECUTE
                                </Button>
                            </Grid>
                        </Grid><p/><p/>
                        <Box>
                            {inputFields}
                        </Box><p/><p/>
                        {
                            displayResult ?
                                <div>
                                    {
                                        !this.state.response.success ?
                                            <Typography>
                                                {this.state.response.message}
                                            </Typography> :
                                            <div>
                                                <NodesResponse
                                                    nodes={this.state.response.nodes}
                                                    handleGraphUpdate={this.props.handleGraphUpdate}
                                                    graph={this.props.graph}
                                                />
                                                <EdgesResponse
                                                    edges={this.state.response.edges}
                                                    handleGraphUpdate={this.props.handleGraphUpdate}
                                                    graph={this.props.graph}
                                                />
                                                <ComponentsResponse
                                                    components={this.state.response.components}
                                                    handleGraphUpdate={this.props.handleGraphUpdate}
                                                    graph={this.props.graph}
                                                />
                                            </div>
                                    }
                                </div>
                            : <div/>
                        }
                    </Box>
                </Paper>
            </Box>
        )
    }
}

class NodesResponse extends React.Component {
    selectNode = (id) => {
        this.props.graph.nodes.forEach((n) => {
            n.selected = n.id === id;
            n.highlighted = false;
        })
        this.props.handleGraphUpdate(this.props.graph);
    }
    highlightNode = (id) => {
        this.props.graph.nodes.forEach((n) => {
            n.highlighted = n.id === id;
        })
        this.props.handleGraphUpdate(this.props.graph);
    }
    selectAll = () => {
        for (let i = 0; i < this.props.graph.nodes.length; i++) {
            this.props.graph.nodes[i].selected = false;
            this.props.graph.nodes[i].highlighted = false;
        }
        for (let i = 0; i < this.props.nodes.length; i++) {
            this.props.graph.nodes.find(n => n.id === this.props.nodes[i]).selected = true;
        }
        this.props.handleGraphUpdate(this.props.graph);
    }
    render() {
        if (this.props.nodes.length === 0) return <div />

        return <Box m = {2}>
            <Grid
                container
                direction="row"
                justify="space-between"
                alignItems="center"
                wrap="nowrap"
            >
                <Grid item>
                    {
                        this.props.nodes.map(n => {
                            const currNode = this.props.graph.nodes.find(nn => n === nn.id);
                            return <Node
                                key={n}
                                id={n}
                                selected={currNode === undefined ? false : currNode.selected}
                                highlighted={currNode === undefined ? false : currNode.highlighted}
                                selectNode={this.selectNode}
                                highlightNode={this.highlightNode}
                            />
                        })
                    }
                </Grid>
                <Grid item>
                    <Button onClick={this.selectAll}>
                        SELECT
                    </Button>
                </Grid>
            </Grid>
        </Box>
    }
}
class Node extends React.Component {

    onMouseEnter = () => {
        this.props.highlightNode(this.props.id);
    }
    onMouseLeave = () => {
        this.props.highlightNode(-1);
    }
    onClick = () => {
        this.props.selectNode(this.props.id);
    }

    render() {
        return <Chip
            color={this.props.selected || this.props.highlighted ? "primary" : undefined}
            onMouseEnter={this.onMouseEnter}
            onMouseLeave={this.onMouseLeave}
            onClick={this.onClick}
            style = {{margin: 2}}
            label={this.props.id} />
    }
}
class EdgesResponse extends React.Component {
    selectEdge = (edge) => {
        this.props.graph.edges.forEach((e) => {
            e.selected = e.fromId === edge.from && e.toId === edge.to;
            e.highlighted = false;
        })
        this.props.handleGraphUpdate(this.props.graph);
    }
    highlightEdge = (edge) => {
        this.props.graph.edges.forEach((e) => {
            e.highlighted = e.fromId === edge.from && e.toId === edge.to;
        })
        this.props.handleGraphUpdate(this.props.graph);
    }
    selectAll = () => {
        for (let i = 0; i < this.props.graph.edges.length; i++) {
            this.props.graph.edges[i].selected = false;
            this.props.graph.edges[i].highlighted = false;
        }
        for (let i = 0; i < this.props.edges.length; i++) {
            this.props.graph.edges.find(e => e.fromId === this.props.edges[i].from && e.toId === this.props.edges[i].to).selected = true;
        }
        this.props.handleGraphUpdate(this.props.graph);
    }
    render() {
        if (this.props.edges.length === 0) return <div />
        return  <Grid
            container
            direction="row"
            justify="space-between"
            alignItems="center"
            wrap="nowrap"
        >
            <Grid item>
                        {
                            this.props.edges.map(e => {
                                const currEdge = this.props.graph.edges.find(ee => e.to === ee.toId && e.from === ee.fromId);
                                return <Edge
                                    key={e.to+"->"+e.from}
                                    from={e.from}
                                    to={e.to}
                                    selected={currEdge === undefined ? false : currEdge.selected}
                                    highlighted={currEdge === undefined ? false : currEdge.highlighted}
                                    selectEdge={this.selectEdge}
                                    highlightEdge={this.highlightEdge}
                                />
                            })
                        }
            </Grid>
            <Grid item>
                <Button onClick={this.selectAll}>
                    SELECT
                </Button>
            </Grid>
        </Grid>
    }
}
class Edge extends React.Component {

    onMouseEnter = () => {
        this.props.highlightEdge({
            from: this.props.from,
            to: this.props.to
        });
    }
    onMouseLeave = () => {
        this.props.highlightEdge({
            from: -1,
            to: -1
        });
    }
    onClick = () => {
        this.props.selectEdge({
            from: this.props.from,
            to: this.props.to
        });
    }

    render() {
        return <Chip
            color={this.props.selected || this.props.highlighted ? "primary" : undefined}
            onMouseEnter={this.onMouseEnter}
            onMouseLeave={this.onMouseLeave}
            onClick={this.onClick}
            style = {{margin: 2}}
            label={this.props.from + " → " + this.props.to}/>
    }
}
class ComponentsResponse extends React.Component {
    render() {
        if (this.props.components.length === 0) return <div />
        return <Box>
            {
                this.props.components.map(c => {
                    return <NodesResponse
                        key={c.join("")}
                        nodes={c}
                        handleGraphUpdate={this.props.handleGraphUpdate}
                        graph={this.props.graph}
                    />
                })
            }
        </Box>
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
