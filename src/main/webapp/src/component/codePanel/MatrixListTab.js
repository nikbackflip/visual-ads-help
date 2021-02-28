import React from 'react';
import MatrixDisplay from "./MatrixDisplay";
import ListDisplay from "./ListDisplay";


class MatrixListTab extends React.Component {

    render() {
        return (
            <div className="Code-panel-whole-height">
                <div className="Code-panel-half-height">
                    <MatrixDisplay
                        graph={this.props.graph}
                        config={this.props.config}
                        handleGraphUpdate={this.props.handleGraphUpdate}
                        stageHeight={this.props.stageHeight}
                        stageWidth={this.props.stageWidth}
                    />
                </div>
                <div className="Code-panel-space"/>
                <div className="App-line-split"/>
                <div className="Code-panel-half-height">
                    <ListDisplay
                        graph={this.props.graph}
                        config={this.props.config}
                        handleGraphUpdate={this.props.handleGraphUpdate}
                        stageHeight={this.props.stageHeight}
                        stageWidth={this.props.stageWidth}
                    />
                </div>
            </div>
        )
    }

}

export default MatrixListTab;
