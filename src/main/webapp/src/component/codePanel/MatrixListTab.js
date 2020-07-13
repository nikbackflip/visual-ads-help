import React from 'react';
import {ListDisplay, MatrixDisplay} from "./EditableView";


class MatrixListTab extends React.Component {

    constructor(props) {
        super(props);
        this.state = {};
    }

    componentDidMount() {
        this.fetchMatrix();
        this.fetchList();
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
        if (JSON.stringify(prevProps) !== JSON.stringify(this.props)) {
            this.fetchMatrix();
            this.fetchList();
        }
    }

    fetchMatrix = () => {
        let self = this;
        fetch('/generator/matrix', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(this.props.graph)
        }).then((response) => {
            return response.json();
        }).then((data) => {
            self.setState({codeMatrix: data.generated});
        });
    }

    fetchList = () => {
        let self = this;
        fetch('/generator/list', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(this.props.graph)
        }).then((response) => {
            return response.json();
        }).then((data) => {
            self.setState({codeList: data.generated});
        });
    }

    render() {
        return (
            <div className="Code-panel-whole-height">
                <div className="Code-panel-half-height">
                    <MatrixDisplay
                        code={this.state.codeMatrix}
                    />
                </div>
                <div className="Code-panel-space"/>
                <div className="App-line-split"/>
                <div className="Code-panel-half-height">
                    <ListDisplay
                        code={this.state.codeList}
                    />
                </div>
            </div>
        )
    }

}

export default MatrixListTab;
