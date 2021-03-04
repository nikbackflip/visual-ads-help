import {
    ABSENT_DIRECTION, BOTH_DIRECTIONS,
    colors,
    FORWARD_DIRECTION, NO_DIRECTIONS,
    SELF_DIRECTION, SPLIT_DIRECTION
} from "../component/drawingArea/DrawingModeConstants";

export function internalGraphToMatrix(graph) {
    const n = graph.nodes.length;

    //create nxn matrix and fill with 0
    let code = Array.from(Array(n), _ => Array(n).fill(0.0));

    //filer placeholder absent edges
    let edges = graph.edges.slice().filter(e => e.direction !== ABSENT_DIRECTION);

    //set weights
    edges.forEach(e => {
        code[e.fromId][e.toId] = e.weight;
    })
    return code;
}

export function internalGraphToList(graph) {
    const n = graph.nodes.length;

    let code = [];
    [...Array(n).keys()].forEach(() => {
        code.push([]);
    });

    //filer placeholder absent edges
    let edges = graph.edges.slice().filter(e => e.direction !== ABSENT_DIRECTION);

    edges.forEach(e => {
        code[e.fromId].push(e.toId);
    });
    return code;
}

export function matrixToInternalGraph(matrix, config, coordinates) {
    let edges = [];
    let nextEdgeId = 0;
    let n = Object.keys(matrix).length;

    let nodes = createNodes(n, coordinates);

    //create edges
    for (let i = 0; i < n; i++) {
        matrix[i].forEach((weight, j) => {
            if (weight !== 0) {
                edges.push({
                    id: nextEdgeId++,
                    fromId: i,
                    toId: j,
                    weight: weight
                });
            }
        })
    }

    //set edge directions and create placeholder absent edges
    let pairs = [];
    edges.forEach(e => {
        if (e.pairId === undefined) {
            if (e.fromId === e.toId) {
                e.pairId = e.id;
                e.direction = SELF_DIRECTION;
                return;
            }
            let pair = edges.find(p => p.fromId === e.toId && p.toId === e.fromId);
            if (pair === undefined) {
                pair = {
                    id: nextEdgeId++,
                    pairId: e.id,
                    fromId: e.toId,
                    toId: e.fromId,
                    weight: 0,
                    direction: ABSENT_DIRECTION
                }
                pairs.push(pair);
                e.pairId = pair.id;
                e.direction = FORWARD_DIRECTION;
            } else {
                if (e.weight !== pair.weight) {
                    e.direction = SPLIT_DIRECTION;
                    pair.direction = SPLIT_DIRECTION;
                } else {
                    e.direction = config.graphDirectional ? BOTH_DIRECTIONS : NO_DIRECTIONS;
                    pair.direction = config.graphDirectional ? BOTH_DIRECTIONS : NO_DIRECTIONS;
                }
                e.pairId = pair.id;
                pair.pairId = e.id;
            }
        }
    })
    edges.push(...pairs);

    return {
        nodes: nodes,
        edges: edges
    }
}

export function listToInternalGraph(list, config, coordinates) {
    let edges = [];
    let nextEdgeId = 0;
    let n = Object.keys(list).length;

    let nodes = createNodes(n, coordinates);

    //create edges
    for (let i = 0; i < n; i++) {
        list[i].forEach(toNode => {
            edges.push({
                id: nextEdgeId++,
                fromId: i,
                toId: toNode,
                weight: 1
            });
        })
    }

    //set edge directions and create placeholder absent edges
    let pairs = [];
    edges.forEach(e => {
        if (e.pairId === undefined) {
            if (e.fromId === e.toId) {
                e.pairId = e.id;
                e.direction = SELF_DIRECTION;
                return;
            }
            let pair = edges.find(p => p.fromId === e.toId && p.toId === e.fromId);
            if (pair === undefined) {
                pair = {
                    id: nextEdgeId++,
                    pairId: e.id,
                    fromId: e.toId,
                    toId: e.fromId,
                    weight: 0,
                    direction: ABSENT_DIRECTION
                }
                pairs.push(pair);
                e.pairId = pair.id;
                e.direction = FORWARD_DIRECTION;
            } else {
                e.direction = config.graphDirectional ? BOTH_DIRECTIONS : NO_DIRECTIONS;
                pair.direction = config.graphDirectional ? BOTH_DIRECTIONS : NO_DIRECTIONS;
                e.pairId = pair.id;
                pair.pairId = e.id;
            }
        }
    })
    edges.push(...pairs);

    return {
        nodes: nodes,
        edges: edges
    }
}

function createNodes(n, coordinates) {
    let nodes = [];

    //create nodes
    if (coordinates === undefined || coordinates === null) {
        [...Array(n).keys()].forEach(i => {
            nodes.push({id: i});
        });
    } else {
        [...Array(n).keys()].forEach(i => {
            nodes.push({
                id: i,
                x: coordinates[i].x,
                y: coordinates[i].y
            });
        });
    }

    return nodes;
}
