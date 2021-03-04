import React from 'react';
import {UnControlled} from "react-codemirror2";
import {ABSENT_DIRECTION} from "../drawingArea/DrawingModeConstants";


class JavaTab extends React.Component {

    constructor(props) {
        super(props);
        this.state = {};
    }

    componentDidMount() {
        this.fetchGraphCode();
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
        if (JSON.stringify(prevProps) !== JSON.stringify(this.props)) {
            this.fetchGraphCode();
        }
    }

    fetchGraphCode = () => {
        let graph = {
            edges: this.props.graph.edges.slice().filter(e => e.direction !== ABSENT_DIRECTION),
            nodes: this.props.graph.nodes
        }
        let self = this;
        fetch('/template/graph', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(graph)
        }).then((response) => {
            return response.json();
        }).then((data) => {
            self.setState({code: data.generated});
        });
    }

    render() {
        return (
            <UnControlled
                value={this.state.code}
                options={{
                    lineNumbers: true,
                    mode: "text/x-java",
                    matchBrackets: true,
                    // theme: "darcula",
                    scrollbarStyle: null,
                    readOnly: true
                }}
                onBeforeChange={(editor, data, value) => {
                }}
                onChange={(editor, data, value) => {
                }}
            />
        )
    }


}

export default JavaTab;
