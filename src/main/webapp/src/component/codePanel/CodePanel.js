import React from 'react';

import 'codemirror/lib/codemirror.css';
import 'codemirror/theme/darcula.css';
import 'codemirror/mode/clike/clike.js';
import {UnControlled} from "react-codemirror2";
import '../css/CodePanel.css';


class CodePanel extends React.Component {

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
            return response.text();
        }).then((data) => {
            self.setState({code: data});
        });
    }

    // downloadGraphFile = () => {
    //     fetch('/generator/graph', {
    //         method: 'POST',
    //         headers: {
    //             'Content-Type': 'application/json'
    //         },
    //         body: JSON.stringify(this.props.graph)
    //     }).then(response =>
    //         response.blob().then(blob => {
    //             let url = window.URL.createObjectURL(blob);
    //             let a = document.createElement('a');
    //             a.href = url;
    //             a.download = 'Graph.java';
    //             a.click();
    //         }));
    // }

    render() {
        console.log("Rendering code panel");

        return (
            <div className="App-code-panel">
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
            </div>
        );
    }
}

export default CodePanel;
