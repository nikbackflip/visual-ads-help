import React from "react";


class ViewControlPanel extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            inEditMode: false
        };
    }

    editButton = () => {
        return (
            <button
                className={"Control-panel-button"}
                onClick={
                    () => {
                        this.setState({
                            inEditMode: true
                        });
                        this.props.initEdit();
                    }
                }
            >
                <i className="fa fa-edit"/>
            </button>
        )
    }

    okButton = () => {
        return (
            <button
                className={"Control-panel-button"}
                onClick={
                    () => {
                        this.setState({
                            inEditMode: false
                        });
                        this.props.updateGraph();
                    }
                }
            >
                <i className="fa fa-check"/>
            </button>
        )
    }

    cancelButton = () => {
        return (
            <button
                className={"Control-panel-button"}
                onClick={
                    () => {
                        this.setState({
                            inEditMode: false
                        });
                        this.props.cancelEdit();
                    }
                }
            >
                <i className="fa fa-close"/>
            </button>
        )
    }

    render() {
        let buttons;
        if (this.state.inEditMode) {
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