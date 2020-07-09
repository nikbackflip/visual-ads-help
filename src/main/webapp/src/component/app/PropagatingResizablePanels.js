import ResizablePanels from "resizable-panels-react";


class PropagatingResizablePanels extends ResizablePanels {

    componentDidUpdate(prevProps, prevState, snapshot) {
        if (this.state.panelsSize[1] !== prevState.panelsSize[1]) {
            this.props.onUpdate();
        }
    }

}

export default PropagatingResizablePanels;
