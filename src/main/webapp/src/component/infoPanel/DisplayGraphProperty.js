import React from "react";

class DisplayGraphProperty extends React.Component {

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
                        type={this.props.inputType}
                        value={this.props.value}
                        onChange={this.onChange}
                        readOnly={this.props.readOnly}
                        maxLength={this.props.maxLength}
                    />
                </span>
            </div>
        )
    }


}

export default DisplayGraphProperty;