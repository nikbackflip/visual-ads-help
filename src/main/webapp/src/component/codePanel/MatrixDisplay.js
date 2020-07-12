import React from "react";

class MatrixDisplay extends React.Component {

    render() {
        let header = null
        let body = null;
        if (this.props.code !== undefined) {
            const n = Object.keys(this.props.code).length;
            header =
                <thead>
                    <tr>
                        <th />
                        {Array.from(Array(n).keys()).map(i => {
                            let headerIndex = i;
                            if (i >= 0 && i < 10) {
                                headerIndex = i + "  ";
                            }
                            return <th className="Code-panel-theader" scope="col">{headerIndex}</th>
                        })}
                    </tr>
                </thead>

            body =
                <tbody>
                    {this.props.code.map((node, i) => {
                        return (
                            <tr>
                                <th className="Code-panel-theader" scope="row">{i}</th>
                                    {node.map(el => {
                                        let element = el;
                                        if (el === null) {
                                            element = " ";
                                        }
                                        return <td  className="Code-panel-tbody">{element}</td>
                                    })}
                            </tr>)
                    })}
                </tbody>
        }

        return (
            <div className="Code-panel-half-height Code-panel-scroll">
                <table>
                    {header}
                    {body}
                </table>
            </div>
        )
    }
    
}

export default MatrixDisplay;