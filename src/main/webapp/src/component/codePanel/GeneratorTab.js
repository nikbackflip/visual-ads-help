import React from "react";
import {matrixToInternalGraph} from "../../util/GraphTransformationUtil";

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
        return <div className="Code-panel-options">

            <div>
                <div>
                    <p/>
                    Selected:
                    <p/>
                </div>
                <div className="Code-panel-row">
                    {
                        this.state.selectedOptions.map(n => {
                            return <Option
                                key={n}
                                id={n}
                                onClick={this.deselectOption}
                            />
                        })
                    }
                </div>
            </div>

            <div>
                <div>
                    <p/>
                    Select Options:
                    <p/>
                </div>
                <div className="Code-panel-row">
                    {
                        this.state.availableOptions.map(n => {
                            return <Option
                                key={n}
                                id={n}
                                onClick={this.selectOption}
                            />
                        })
                    }
                </div>
            </div>

            <button
                className="Control-panel-button Code-panel-generate-button"
                onClick={this.generateGraph}
            >
                GENERATE
            </button>


        </div>
    }
}

class Option extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            highlighted: false
        };
    }

    onMouseEnter = () => {
        this.setState({
            highlighted: true
        })
    }
    onMouseLeave = () => {
        this.setState({
            highlighted: false
        })
    }
    onClick = () => {
        this.props.onClick(this.props.id);
    }

    render() {
        let style = "Code-panel-option ";
        if (this.state.highlighted) style = style + "Code-panel-highlight-half";
        return <div
            className={style}
            onMouseEnter={this.onMouseEnter}
            onMouseLeave={this.onMouseLeave}
            onClick={this.onClick}
        >
            {this.props.id}
        </div>
    }
}

export default GeneratorTab;

