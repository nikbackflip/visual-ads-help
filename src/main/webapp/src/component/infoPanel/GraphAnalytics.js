import React from "react";
import {ABSENT_DIRECTION} from "../drawingArea/DrawingModeConstants";

class FetchingAnalytic extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            analytics: []
        };
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
        fetch("/analytics", {
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
            self.setState({analytics: data.analytics});
        });
    }

    render() {
        return (
            <div>
                {
                    this.state.analytics.map((analytic) => {
                        return <Analytic
                            key={analytic.name}
                            label={analytic.name}
                            checked={analytic.result}
                        />
                    })
                }
            </div>
            )
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
                <FetchingAnalytic
                    config={this.props.config}
                    graph={this.props.graph}
                />
                <div className="App-line-split"/>
            </div>
        );
    }
}

export default GraphAnalytics;