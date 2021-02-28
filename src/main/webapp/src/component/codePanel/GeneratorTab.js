import React from "react";
import {
    ABSENT_DIRECTION, BOTH_DIRECTIONS, colors,
    FORWARD_DIRECTION, NO_DIRECTIONS,
    SELF_DIRECTION,
    SPLIT_DIRECTION
} from "../drawingArea/DrawingModeConstants";

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
        fetch('/generator/generate?for=' + options + '&x=' + Math.floor(this.props.stageWidth) + '&y=' + Math.floor(this.props.stageHeight), {
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
        let nodes = [];
        let edges = [];
        let nextEdgeId = 0;
        let n = matrix.length;

        //create nodes
        if (coordinates === undefined || coordinates === null) {
            [...Array(n).keys()].forEach(i => {
                nodes.push({id: i});
            });
        } else {
            [...Array(n).keys()].forEach(i => {
                nodes.push({
                    name: i,
                    id: i,
                    color: colors.sample(),
                    selected: false,
                    highlighted: false,
                    x: coordinates[i].x,
                    y: coordinates[i].y
                });
            });
        }

        //create edges
        for (let i = 0; i < n; i++) {
            matrix[i].forEach((weight, j) => {
                if (weight !== 0) {
                    edges.push({
                        id: nextEdgeId++,
                        fromId: i,
                        toId: j,
                        weight: weight
                    });
                }
            })
        }

        //set edge directions and create placeholder absent edges
        let pairs = [];
        edges.forEach(e => {
            if (e.pairId === undefined) {
                if (e.fromId === e.toId) {
                    e.pairId = e.id;
                    e.direction = SELF_DIRECTION;
                    return;
                }
                let pair = edges.find(p => p.fromId === e.toId && p.toId === e.fromId);
                if (pair === undefined) {
                    pair = {
                        id: nextEdgeId++,
                        pairId: e.id,
                        fromId: e.toId,
                        toId: e.fromId,
                        weight: 0,
                        direction: ABSENT_DIRECTION
                    }
                    pairs.push(pair);
                    e.pairId = pair.id;
                    e.direction = FORWARD_DIRECTION;
                } else {
                    if (e.weight !== pair.weight) {
                        e.direction = SPLIT_DIRECTION;
                        pair.direction = SPLIT_DIRECTION;
                    } else {
                        e.direction = config.graphDirectional ? BOTH_DIRECTIONS : NO_DIRECTIONS;
                        pair.direction = config.graphDirectional ? BOTH_DIRECTIONS : NO_DIRECTIONS;
                    }
                    e.pairId = pair.id;
                    pair.pairId = e.id;
                }
            }
        })
        edges.push(...pairs);

        //submit graph
        this.props.handleGraphUpdate({
            nodes: nodes,
            edges: edges
        });
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

