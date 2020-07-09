import React from 'react';
import {UnControlled} from "react-codemirror2";


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
        let self = this;
        fetch('/generator/graph', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(this.props.graph)
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
                    theme: "darcula",
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
