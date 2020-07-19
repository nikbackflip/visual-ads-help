export const MODE_NONE = 0;
export const MODE_ADD_NODE = 1;
export const MODE_ADD_EDGE = 2;
export const MODE_DEL_NODE = 3;
export const MODE_DEL_EDGE = 4;

export const BUTTON_NAMES = Object.freeze({
    0: "Select",
    1: "Add Node",
    2: "Add Edge",
    3: "Del Node",
    4: "Del Edge"
})

export const colors = [
    '#FF7EC7',
    '#BF42FF',
    '#8A4BFF',
    '#4C80FF'];

export const circleRadius = 20;

export const NO_DIRECTIONS = "noDirections";
export const BOTH_DIRECTIONS = "bothDirections";
export const FORWARD_DIRECTION = "forwardDirection";
export const REVERSE_DIRECTION = "reverseDirection";
export const SELF_DIRECTION = "selfDirection";

export const EDGE_DIRECTION_NAMES = Object.freeze({
    "noDirections": "No",
    "bothDirections": "Both",
    "forwardDirection": "Forward",
    "reverseDirection": "Reverse",
})