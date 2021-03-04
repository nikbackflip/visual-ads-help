import React from 'react';
import MatrixDisplay from "./MatrixDisplay";
import ListDisplay from "./ListDisplay";
import {Box} from "@material-ui/core";


class MatrixListTab extends React.Component {

    render() {
        return (
            <Box className="full-height-code">
                <Box m={2}
                     className="half-height-code"
                     overflow="hidden"
                >
                    <MatrixDisplay
                        graph={this.props.graph}
                        config={this.props.config}
                        handleGraphUpdate={this.props.handleGraphUpdate}
                        stage={this.props.stage}
                    />
                </Box>
                <Box m={2}
                     className="half-height-code"
                     overflow="hidden"
                >
                    <ListDisplay
                        graph={this.props.graph}
                        config={this.props.config}
                        handleGraphUpdate={this.props.handleGraphUpdate}
                        stage={this.props.stage}
                    />
                </Box>
            </Box>
        )
    }

}

export default MatrixListTab;
