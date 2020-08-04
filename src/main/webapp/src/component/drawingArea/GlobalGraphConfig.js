import React from "react";
import {withStyles} from "@material-ui/core/styles";
import Switch from "@material-ui/core/Switch";


const ColoredSwitch = withStyles({
    switchBase: {
        color: '#1d9797',
        '&$checked': {
            color: '#1d9797',
        },
        '&$checked + $track': {
            backgroundColor: '#1d9797',
        },
    },
    checked: {},
    track: {},
})(Switch);

class GlobalGraphConfig extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            graphDirectional: false,
            graphWeighted: false,
            selfEdgesAllowed: false
        };
    }

    componentDidMount() {
       this.setState({
           graphDirectional: this.props.config.graphDirectional,
           graphWeighted: this.props.config.graphWeighted,
           selfEdgesAllowed: this.props.config.selfEdgesAllowed
       });
    }

    edit = (config, checked) => {
        let newState = Object.assign({}, this.state);
        newState[config] = checked;
        this.setState(newState);
    }

    configsUpdated = () => {
        return this.state.graphDirectional !== this.props.config.graphDirectional ||
        this.state.graphWeighted !== this.props.config.graphWeighted ||
        this.state.selfEdgesAllowed !== this.props.config.selfEdgesAllowed;
    }

    render() {

        return (
            <div className="Drawing-area-config">
                <div className="App-line-split"/>
                <div className="Drawing-area-config-item">
                    <label>Directional</label>
                    <ColoredSwitch
                        className="Drawing-area-config-toggle"
                        checked={this.state.graphDirectional}
                        onChange={e => {
                            e.persist();
                            this.edit("graphDirectional", e.target.checked)
                        }}
                        size="small"
                        color="primary"
                        inputProps={{ 'aria-label': 'primary checkbox' }}
                    />
                </div>
                <div className="Drawing-area-config-item">
                    <label>Weighted</label>
                    <ColoredSwitch
                        className="Drawing-area-config-toggle"
                        checked={this.state.graphWeighted}
                        onChange={e => {
                            e.persist();
                            this.edit("graphWeighted", e.target.checked)
                        }}
                        size="small"
                        color="primary"
                        inputProps={{ 'aria-label': 'primary checkbox' }}
                    />
                </div>
                <div className="Drawing-area-config-item">
                    <label>Self Edges</label>
                    <ColoredSwitch
                        className="Drawing-area-config-toggle"
                        checked={this.state.selfEdgesAllowed}
                        onChange={e => {
                            e.persist();
                            this.edit("selfEdgesAllowed", e.target.checked)
                        }}
                        size="small"
                        color="primary"
                        inputProps={{ 'aria-label': 'primary checkbox' }}
                    />
                </div>

                {
                    this.configsUpdated() ?
                        <div className="Drawing-area-config-help">
                            Applying changes will reset current graph
                        </div>
                    : null
                }

                <div className="Drawing-area-config-submit">
                    <button
                        className={"Control-panel-button Drawing-area-config-button"}
                        onClick={() => {
                            if (this.configsUpdated())
                                this.props.applyConfig(
                                    this.state.graphDirectional,
                                    this.state.graphWeighted,
                                    this.state.selfEdgesAllowed
                                )
                            else this.props.cancelConfig()
                        }}
                    >
                        Apply
                    </button>
                    <button
                        className={"Control-panel-button Drawing-area-config-button"}
                        onClick={this.props.cancelConfig}
                    >
                        Cancel
                    </button>
                </div>

            </div>
        )
    }

}

export default GlobalGraphConfig;