import React from "react";

class UpdateGraphProperty extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            value: props.value,
        };
    }

    onChange = (e) => {
        this.setState({
            value: e.target.value
        });
        this.props.updateElementProperty(this.props.propertyName, e.target.value)
    }

    render() {
        return (
            <div>
                <label className="Info-panel-text">{this.props.label}: </label>
                <input
                    className="Info-panel-input"
                    type="text"
                    value={this.state.value}
                    onChange={this.onChange}
                />
            </div>
        )
    }


}

export default UpdateGraphProperty;