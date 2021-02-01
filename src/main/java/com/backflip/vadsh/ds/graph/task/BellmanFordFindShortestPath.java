package com.backflip.vadsh.ds.graph.task;

import com.backflip.vadsh.ds.graph.Config;
import com.backflip.vadsh.ds.graph.Edge;
import com.backflip.vadsh.ds.graph.Graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.backflip.vadsh.ds.graph.task.TaskResult.failure;
import static com.backflip.vadsh.ds.graph.task.TaskResult.successEdges;

public class BellmanFordFindShortestPath implements Task {

    @Override
    public TaskResult execute(Graph graph, Config config, Map<String, Integer> params) {
        List<List<Edge>> g = graph.adjacencyListOfEdges();
        int n = graph.n();
        int from = params.get("from");
        int to = params.get("to");

        float[] dist = new float[n];
        for (int i = 0; i < n; i++) {
            dist[i] = Float.POSITIVE_INFINITY;
        }
        int[] route = new int[n];
        for (int i = 0; i < n; i++) {
            route[i] = -1;
        }

        dist[from] = 0;
        route[from] = -1;
        for (int j = 0; j < n - 1; j++) {
            for (List<Edge> edges : g) {
                for (Edge e : edges) {
                    if (dist[e.from()] + e.weight() < dist[e.to()]) {
                        dist[e.to()] = dist[e.from()] + (float) e.weight();
                        route[e.to()] = e.from();
                    }
                }
            }
        }
        for (int j = 0; j < n - 1; j++) {
            for (List<Edge> edges : g) {
                for (Edge e : edges) {
                    if (dist[e.from()] + e.weight() < dist[e.to()]) {
                        dist[e.to()] = Float.NEGATIVE_INFINITY;
                    }
                }
            }
        }

        if (dist[to] == Float.NEGATIVE_INFINITY) return failure("Negative cycle detected");

        List<Edge> path = new ArrayList<>();
        int fromNode = route[to];
        int toNode = to;
        while (fromNode != -1) {
            path.add(new Edge(fromNode, toNode, 1.0));
            toNode = fromNode;
            fromNode = route[toNode];
        }
        Collections.reverse(path);
        return successEdges(path);
    }
}
