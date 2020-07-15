import React from "react";

class DisplayTextGraphProperty extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            value: this.props.value
        }
    }

    onInputEnd = (e) => {
        if (this.props.inputIsValid(e.target.value)) {
            this.props.updateElementProperty(this.props.propertyName, e.target.value)
        } else {
            this.setState({
                value: this.props.value
            })
        }
    }

    onChange = (e) => {
        this.setState({
            value: e.target.value
        })
    }

    render() {
        return (
            <div className="Info-panel-div">
                <label className="Info-panel-text">{this.props.label}: </label>
                <span
                    className="Info-panel-span">
                    <input
                        className="Info-panel-input"
                        type={this.props.inputFormat == null ? "text" : this.props.inputFormat}
                        value={this.state.value}
                        onBlur={this.onInputEnd}
                        onChange={this.onChange}
                        readOnly={this.props.readOnly == null ? false : this.props.readOnly}
                        maxLength={this.props.maxLength}
                    />
                </span>
            </div>
        )
    }
}

export default DisplayTextGraphProperty;