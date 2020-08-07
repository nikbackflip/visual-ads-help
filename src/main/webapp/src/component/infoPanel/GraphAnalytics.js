import React from "react";
import {ABSENT_DIRECTION} from "../drawingArea/DrawingModeConstants";

class DirectionalAnalytic extends React.Component {

    render() {
        return <Analytic
            label="directional"
            checked={this.props.config.graphDirectional}
        />
    }
}

class WeightedAnalytic extends React.Component {

    render() {
        return <Analytic
            label="weighted"
            checked={this.props.config.graphWeighted}
        />
    }
}

class CompleteAnalytic extends React.Component {

    render() {
        return <FetchingAnalytic
            label="complete"
            analyticApi={"/complete?selfLoops=" + this.props.config.selfEdgesAllowed}
            graph={this.props.graph}
        />
    }
}


class FetchingAnalytic extends React.Component {

    constructor(props) {
        super(props);
        this.state = {};
    }

    componentDidMount() {
        this.getAnalytic();
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
        if (JSON.stringify(prevProps) !== JSON.stringify(this.props)) {
            this.getAnalytic();
        }
    }

    getAnalytic = () => {
        const graph = {
            edges: this.props.graph.edges.slice().filter(e => e.direction !== ABSENT_DIRECTION),
            nodes: this.props.graph.nodes
        }
        let self = this;
        fetch("/analyzer" + this.props.analyticApi, {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(graph)
        }).then((response) => {
            return response.json();
        }).then((data) => {
            self.setState({checked: data.checked});
        });
    }

    render() {
        return <Analytic
            label={this.props.label}
            checked={this.state.checked}
        />
    }
}

class Analytic extends React.Component {
    render() {
        return (
            <div className="Info-panel-analytic">
                <span>{" • " + this.props.label + ": "}</span>
                {
                    this.props.checked ?
                        <i className="fa fa-check Info-panel-analytic-green" aria-hidden="true"/> :
                        <i className="fa fa-close Info-panel-analytic-red" aria-hidden="true"/>
                }
            </div>
        )
    }
}

class GraphAnalytics extends React.Component {

    graphIsEmpty = () => {
        return this.props.graph.nodes.length === 0;
    }

    render() {
        if (this.graphIsEmpty()) return null;
        return (
            <div>
                <div className="Info-panel-label">Graph is:</div>
                <DirectionalAnalytic
                    config={this.props.config}
                />
                <WeightedAnalytic
                    config={this.props.config}
                />
                <CompleteAnalytic
                    config={this.props.config}
                    graph={this.props.graph}
                />
                {/*<Analytic*/}
                {/*    label="tree"*/}
                {/*    checked={true}*/}
                {/*/>*/}
                {/*<Analytic*/}
                {/*    label="DAG"*/}
                {/*    checked={false}*/}
                {/*/>*/}

                <div className="App-line-split"/>
            </div>
        );
    }
}

export default GraphAnalytics;