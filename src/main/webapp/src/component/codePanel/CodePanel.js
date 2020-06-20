import React from 'react';

class CodePanel extends React.Component {

    handleClick = () => {
        fetch('http://localhost:8080/api/download', {
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
                    className="Drawing-area-header-button"
                    onClick={() => this.handleClick()}
                    >
                    DOWNLOAD
                </button>
            </div>
        );
    }
}

export default CodePanel;
