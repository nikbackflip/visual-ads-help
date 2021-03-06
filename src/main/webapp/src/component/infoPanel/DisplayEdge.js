import React from "react";
import DisplayTextGraphProperty from "./DisplayTextGraphProperty";
import DisplayDropdownGraphProperty from "./DisplayDropdownGraphProperty";
import {
    ABSENT_DIRECTION, BOTH_DIRECTIONS,
    EDGE_DIRECTION_SELF,
    FORWARD_DIRECTION,
    NO_DIRECTIONS,
    SELF_DIRECTION,
    SPLIT_DIRECTION,
    getDirectionOptions
} from "../drawingArea/DrawingModeConstants";
import {Button, Paper} from "@material-ui/core";
import SwapHorizIcon from '@material-ui/icons/SwapHoriz';


class DisplayEdge extends React.Component {

    updateDirection = (direction) => {
        let updatedEdge = Object.assign({}, this.props.element);
        let updatedPair = Object.assign({}, this.props.pair);
        updatedEdge.direction = direction;
        updatedPair.direction = direction === FORWARD_DIRECTION ? ABSENT_DIRECTION : direction;
        updatedPair.weight = updatedEdge.weight;

        this.props.updateBoth(updatedEdge, updatedPair);
    }

    updateWeight = (newForward, newReverse) => {
        let updatedEdge = Object.assign({}, this.props.element);
        let updatedPair = Object.assign({}, this.props.pair);

        switch (updatedEdge.direction) {
            case NO_DIRECTIONS:
            case BOTH_DIRECTIONS:
                const newWeight = updatedEdge.weight === newForward ? newReverse : newForward;
                updatedEdge.weight = newWeight;
                updatedPair.weight = newWeight;
                this.props.updateBoth(updatedEdge, updatedPair);
                break;
            case FORWARD_DIRECTION:
                if (newForward !== updatedEdge.weight) {
                    updatedEdge.weight = newForward;
                }
                this.props.updateBoth(updatedEdge, updatedPair);
                break;
            case SPLIT_DIRECTION:
                updatedEdge.weight = newForward;
                updatedPair.weight = newReverse;
                this.props.updateBoth(updatedEdge, updatedPair);
                break;
            case SELF_DIRECTION:
                updatedEdge.weight = newForward;
                this.props.updateElement(updatedEdge);
                break;
        }
    }

    toggleFromTo = () => {
        let updatedEdge = Object.assign({}, this.props.element);
        let updatedPair = Object.assign({}, this.props.pair);

        switch (updatedEdge.direction) {
            case FORWARD_DIRECTION:
                updatedEdge.direction = ABSENT_DIRECTION;
                updatedPair.direction = FORWARD_DIRECTION;
                updatedPair.weight = updatedEdge.weight;
                updatedPair.selected = updatedEdge.selected;
                updatedPair.highlighted = updatedEdge.highlighted;
                break;
            case SPLIT_DIRECTION:
                updatedEdge.weight = this.props.pair.weight;
                updatedPair.weight = this.props.element.weight;
                break;
        }

        this.props.updateBoth(updatedEdge, updatedPair);
    }

    render() {
        const edge = this.props.element;
        const pair = this.props.pair;
        return this.props.element == null ? <div/> : (
            <Paper elevation={3}>
                <DisplayTextGraphProperty
                    label="From"
                    value={this.props.getNodeName(edge.fromId).name}
                    readOnly={true}
                />
                <DisplayTextGraphProperty
                    label="To"
                    value={this.props.getNodeName(edge.toId).name}
                    readOnly={true}
                />
                <DisplayTextGraphProperty
                    label="Forward cost"
                    propertyName="weight"
                    value={edge.weight}
                    updateElementProperty={(name, value) => {
                        this.updateWeight(value, pair.weight);
                    }}
                    inputIsValid={(input) => {
                        return parseFloat(input) !== 0 && !isNaN(parseFloat(input));
                    }}
                    inputFormat="number"
                    readOnly={!this.props.config.graphWeighted}
                />
                {
                    edge.direction === FORWARD_DIRECTION || pair.direction === ABSENT_DIRECTION || edge.direction === SELF_DIRECTION ? null :
                        <DisplayTextGraphProperty
                            label="Reverse cost"
                            propertyName="weight"
                            value={pair.weight}
                            updateElementProperty={(name, value) => {
                                this.updateWeight(edge.weight, value);
                            }}
                            inputIsValid={(input) => {
                                return parseFloat(input) !== 0 && !isNaN(parseFloat(input));
                            }}
                            inputFormat="number"
                            readOnly={!this.props.config.graphWeighted}
                        />
                }
                <DisplayDropdownGraphProperty
                    label="Direction"
                    propertyName="direction"
                    value={edge.direction}
                    updateElementProperty={(name, value) => {
                        this.updateDirection(value);
                    }}
                    options={edge.direction === SELF_DIRECTION ? EDGE_DIRECTION_SELF : getDirectionOptions(this.props.config)}
                />
                {
                    (edge.direction === FORWARD_DIRECTION || edge.direction === SPLIT_DIRECTION) ?
                        <Button color="secondary" onClick={this.toggleFromTo}>
                            <SwapHorizIcon />
                        </Button>
                        : null
                }
            </Paper>
        );
    }
}

export default DisplayEdge;
