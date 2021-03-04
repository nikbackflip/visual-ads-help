import React from "react";
import {matrixToInternalGraph} from "../../util/GraphTransformationUtil";
import {Box, Button, Chip, Paper, Typography} from "@material-ui/core";

class GeneratorTab extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            availableOptions: [],
            selectedOptions: []
        };
    }

    componentDidMount() {
        this.fetchGeneratorOptions();
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
        if (JSON.stringify(prevState.selectedOptions) !== JSON.stringify(this.state.selectedOptions)) {
            this.fetchGeneratorOptions();
        }
    }

    fetchGeneratorOptions = () => {
        let self = this;

        const options = this.state.selectedOptions.join(",");
        fetch('/generator/options?for=' + options, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        }).then((response) => {
            return response.json();
        }).then((data) => {
            self.setState({
                availableOptions: data.availableOptions
            });
        });
    }

    generateGraph = () => {
        let self = this;

        const options = this.state.selectedOptions.join(",");
        fetch('/generator/generate?for=' + options + '&x=' + Math.floor(this.props.stage.width) + '&y=' + Math.floor(this.props.stage.height), {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        }).then((response) => {
            return response.json();
        }).then((data) => {
            self.resetGraphFromMatrix(data.graph, data.config, data.coordinates)
        });
    }

    resetGraphFromMatrix = (matrix, config, coordinates) => {
        //submit graph
        this.props.handleGraphUpdate(matrixToInternalGraph(matrix, config, coordinates));
        this.props.handleConfigUpdate(config);
    }

    selectOption = (option) => {
        this.setState({
            selectedOptions: [...this.state.selectedOptions, option]
        })
    }

    deselectOption = (option) => {
        this.setState({
            selectedOptions: this.state.selectedOptions.slice().filter(o => o !== option)
        })
    }


    render() {
        const selectedPresent = Boolean(this.state.selectedOptions) && this.state.selectedOptions.length > 0;
        const optionsPresent = Boolean(this.state.availableOptions) && this.state.availableOptions.length > 0;
        return <Box m={2}>
            <Paper elevation={3}>
                <Box overflow="hidden" p={2}>
                    {
                        optionsPresent ?
                            <Box>
                                <Typography variant="h6">Select Options:</Typography>
                                {this.state.availableOptions.map(n => {
                                    return <Chip
                                        key={n}
                                        label={n.replace(/_/g, ' ')}
                                        style = {{margin: 2}}
                                        onClick={() => this.selectOption(n)}/>
                                })}
                            </Box> : <Box/>
                    }
                    <p/>
                    {
                        selectedPresent ?
                            <Box>
                                <Typography variant="h6">Selected:</Typography>
                                {this.state.selectedOptions.map(n => {
                                    return <Chip
                                        key={n}
                                        label={n.replace(/_/g, ' ')}
                                        style={{margin: 2}}
                                        onClick={() => this.deselectOption(n)}/>
                                })}
                            </Box> : <Box/>
                    }
                    <p/>
                    <Button variant="contained" size="large" color="secondary" onClick={this.generateGraph}>
                        GENERATE
                    </Button>
                </Box>
            </Paper>
        </Box>
    }
}

export default GeneratorTab;

