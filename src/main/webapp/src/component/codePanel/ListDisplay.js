import React from "react";

class ListDisplay extends React.Component {

    render() {
        let body = null;

        if (this.props.code !== undefined) {
            body =
                <tbody>
                {this.props.code.map((node, i) => {
                    return (
                        <tr>
                            <th className="Code-panel-theader" scope="row">{i}</th>
                            {node.map(el => {
                                return <td  className="Code-panel-tbody">{el}</td>
                            })}
                        </tr>)
                })}
                </tbody>
        }

        return (
            <div className="Code-panel-half-height Code-panel-scroll">
                <table>
                    {body}
                </table>
            </div>
        )
    }

}

export default ListDisplay;