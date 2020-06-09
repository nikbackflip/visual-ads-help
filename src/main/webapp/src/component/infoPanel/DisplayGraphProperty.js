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
                        type="text"
                        value={this.props.value}
                        onChange={this.onChange}
                        readOnly={this.props.readOnly}
                    />
                </span>
            </div>
        )
    }


}

export default DisplayGraphProperty;