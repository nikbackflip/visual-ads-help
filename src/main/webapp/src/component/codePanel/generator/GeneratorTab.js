import React from "react";
import {matrixToInternalGraph} from "../../../util/GraphTransformationUtil";
import {Box, Button, Paper} from "@material-ui/core";
import { DndProvider } from 'react-dnd'
import { HTML5Backend } from 'react-dnd-html5-backend'
import OptionsContainer from "./OptionsContainer";
import OptionChip from "./OptionChip";
import { OptionTypes } from "../../../util/DndUtil";
import CustomDragLayer from "./OptionDrag";

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
        return <DndProvider backend={HTML5Backend}>
            <Box m={2}>
                <Paper elevation={3}>
                    <Box overflow="hidden" p={2}>
                        <CustomDragLayer/>
                        <OptionsContainer
                            options={this.state.availableOptions.map(n => {
                                    return <OptionChip
                                        key={n}
                                        name={n}
                                        onDrop={() => this.selectOption(n)}
                                        type={OptionTypes.AVAILABLE}
                                    />
                                })}
                            accepts={[OptionTypes.SELECTED]}
                            header={"Select Options"}
                        />
                        <p/>
                        <OptionsContainer
                            options={this.state.selectedOptions.map(n => {
                                    return <OptionChip
                                        key={n}
                                        name={n}
                                        onDrop={() => this.deselectOption(n)}
                                        type={OptionTypes.SELECTED}
                                    />
                                })}
                            accepts={[OptionTypes.AVAILABLE]}
                            header={"Selected"}
                        />
                        <p/>
                        <Button variant="contained" size="large" color="secondary" onClick={this.generateGraph}>
                            GENERATE
                        </Button>
                    </Box>
                </Paper>
            </Box>
        </DndProvider>
    }
}

export default GeneratorTab;

