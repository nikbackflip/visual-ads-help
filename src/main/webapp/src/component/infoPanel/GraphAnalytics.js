import React from "react";
import {ABSENT_DIRECTION} from "../drawingArea/DrawingModeConstants";
import {Typography} from "@material-ui/core";

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
            this.state.analytics.map((analytic) => {
                return <Analytic
                    key={analytic.name}
                    label={analytic.name}
                    checked={analytic.result}
                />
            })
            )
    }
}

class Analytic extends React.Component {
    render() {
        return (
            <div>
                <Typography noWrap>
                    {" • " + this.props.label + ": "}
                {
                    this.props.checked ?
                        <i className="fa fa-check Info-panel-analytic-green" aria-hidden="true"/> :
                        <i className="fa fa-close Info-panel-analytic-red" aria-hidden="true"/>
                }
                </Typography>
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
                <Typography noWrap variant="h6">Graph is:</Typography>
                <FetchingAnalytic
                    config={this.props.config}
                    graph={this.props.graph}
                />
            </div>
        );
    }
}

export default GraphAnalytics;
