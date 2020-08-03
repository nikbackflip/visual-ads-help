import React from "react";


class ViewControlPanel extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            highlighted: false,
            hovering: false
        };
    }

    editButton = () => {
        return (
            <button
                key="edit"
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
                key="ok"
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
                key="cancel"
                className={"Control-panel-button"}
                onClick={this.props.cancelEdit}
            >
                <i className="fa fa-close"/>
            </button>
        )
    }

    infoButton = () => {
        return (
            <button
                key="info"
                className="Control-panel-button"
                onMouseEnter={() => this.setState({hovering: true})}
                onMouseLeave={() => this.setState({hovering: false})}
            >
                <i className="fa fa-question" aria-hidden="true"/>
            </button>
        )
    }

    infoText = () => {
        return (
            this.state.hovering ?
                <div className="Code-panel-help">
                    {this.props.help}
                </div>
            : null
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
            buttons = [this.okButton(), this.cancelButton(), this.infoButton()]
        } else {
            buttons = [this.editButton()];
        }
        return (
            <div className="Control-panel-header Code-panel-text">
                {this.props.header}
                {buttons}
                {this.infoText()}
            </div>
        )
    }

}

export default ViewControlPanel;