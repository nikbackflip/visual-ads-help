import React from "react";


class ViewControlPanel extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            highlighted: false
        };
    }

    editButton = () => {
        return (
            <button
                className={"Control-panel-button"}
                onClick={this.props.initEdit}
            >
                <i className="fa fa-edit"/>
            </button>
        )
    }

    okButton = () => {
        return (
            <button
                className={this.state.highlighted ? "Control-panel-button Control-panel-button-error" : "Control-panel-button" }
                onClick={this.props.updateGraph}
            >
                <i className="fa fa-check"/>
            </button>
        )
    }

    cancelButton = () => {
        return (
            <button
                className={"Control-panel-button"}
                onClick={this.props.cancelEdit}
            >
                <i className="fa fa-close"/>
            </button>
        )
    }

    flash = () => {
        this.setState({ highlighted: true });
        setTimeout(() => {
            this.setState({ highlighted: false });
        }, 1000);
    }

    render() {
        let buttons;
        if (this.props.editMode) {
            buttons = [this.okButton(), this.cancelButton()]
        } else {
            buttons = [this.editButton()];
        }
        return (
            <div className="Control-panel-header Code-panel-text">
                {this.props.header}
                {buttons}
            </div>
        )
    }

}

export default ViewControlPanel;