import '../css/CodePanel.css';
import React from 'react';

class CodePanel extends React.Component {

    downloadGraphFile = () => {
        fetch('/generator/graph', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(this.props.graph)
        }).then(response =>
            response.blob().then(blob => {
                let url = window.URL.createObjectURL(blob);
                let a = document.createElement('a');
                a.href = url;
                a.download = 'Graph.java';
                a.click();
            }));
    }

    render() {
        return (
            <div className="App-code-panel">
                <button
                    className="Code-panel-button"
                    onClick={this.downloadGraphFile}
                >
                    DOWNLOAD
                </button>
            </div>
        );
    }
}

export default CodePanel;
