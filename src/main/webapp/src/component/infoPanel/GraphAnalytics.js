import React from "react";

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
                <Analytic
                    label="directed"
                    checked={true}
                />
                <Analytic
                    label="weighted"
                    checked={false}
                />
                <Analytic
                    label="complete"
                    checked={true}
                />
                <Analytic
                    label="tree"
                    checked={true}
                />
                <Analytic
                    label="DAG"
                    checked={false}
                />

                <div className="App-line-split"/>
            </div>
        );
    }
}

export default GraphAnalytics;