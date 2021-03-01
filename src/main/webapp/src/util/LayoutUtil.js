
export default function layout(graph, config, stage) {
    return fetch("/layout" + '?x=' + Math.floor(stage.width) + '&y=' + Math.floor(stage.height), {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({
            nodes: graph.nodes,
            edges: graph.edges,
            config: config
        })
    }).then((response) => {
        return response.json();
    }).then((data) => {
        let nodes = graph.nodes;
        let coordinates = data.coordinates;

        if (coordinates !== null && coordinates !== undefined) {
            nodes.forEach(i => {
                i.name = i.id;
                i.x = coordinates[i.id].x;
                i.y = coordinates[i.id].y;
            });
        }

        return graph;
    });
}
