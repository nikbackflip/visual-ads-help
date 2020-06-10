import React from "react";

class DisplayTextGraphProperty extends React.Component {

    onChange = (e) => {
        this.props.updateElementProperty(this.props.propertyName, e.target.value)
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
                        value={this.props.value}
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