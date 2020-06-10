import React from "react";


class DisplayDropdownGraphProperty extends React.Component {

    onChange = (e) => {
        this.props.updateElementProperty(this.props.propertyName, e.target.value)
    }

    render() {
        return (
            <div className="Info-panel-div">
                <label className="Info-panel-text">{this.props.label}: </label>
                <span
                    className="Info-panel-span">
                    <select
                        className="Info-panel-input"
                        value={this.props.value}
                        onChange={this.onChange}
                    >

                         {Object.entries(this.props.options).map(d => {
                             console.log(JSON.stringify(d));
                             return (
                                 <option
                                     key={d[0]}
                                     value={d[0]}
                                 >
                                     {d[1]}
                                 </option>)
                         })}
                    </select>
                </span>
            </div>
        )
    }
}

export default DisplayDropdownGraphProperty;