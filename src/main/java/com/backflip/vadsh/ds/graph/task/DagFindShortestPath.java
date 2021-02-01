package com.backflip.vadsh.ds.graph.task;

import com.backflip.vadsh.ds.graph.Config;
import com.backflip.vadsh.ds.graph.Edge;
import com.backflip.vadsh.ds.graph.Graph;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.backflip.vadsh.ds.graph.task.TaskResult.failure;
import static com.backflip.vadsh.ds.graph.task.TaskResult.successEdges;
import static java.lang.String.format;

public class DagFindShortestPath implements Task {

    @Override
    public TaskResult execute(Graph graph, Config config, Map<String, Integer> params) {

        int n = graph.n();
        List<List<Edge>> g = graph.adjacencyListOfEdges();

        int from = params.get("from");
        int to = params.get("to");

        Integer[] sorted = topSort(g);

        int startIndx = 0;
        for (int i = 0; i < n; i++) {
            if (sorted[i] == from) {
                startIndx = i;
                break;
            }
        }

        Float[] dist = new Float[n];
        for (int i = 0; i < n; i++) {
            dist[i] = Float.POSITIVE_INFINITY;
        }

        int[] prev = new int[n];
        for (int i = 0; i < n; i++) {
            prev[i] = -1;
        }

        dist[from] = 0f;
        for (int i = startIndx; i < n; i++) {
            dfs(g, from, dist, prev);
        }

        if (dist[to] == Float.POSITIVE_INFINITY) {
            return failure(format("Node %s is unreachable from node %s", to, from));
        }

        List<Edge> path = new LinkedList<>();
        int cur = to;
        while (cur != from) {
            path.add(new Edge(prev[cur], cur, 1.0));
            cur = prev[cur];
        }
        Collections.reverse(path);

        return successEdges(path);
    }

    private Integer[] topSort(List<List<Edge>> g) {
        List<Integer> sort = new LinkedList<>();
        boolean[] visited = new boolean[g.size()];

        for (int i = 0; i < g.size(); i++) {
            if (!visited[i]) {
                dfs(g, i, sort, visited);
            }
        }

        Collections.reverse(sort);

        return sort.toArray(new Integer[g.size()]);
    }

    private void dfs(List<List<Edge>> g, int i, List<Integer> sort, boolean[] visited) {
        visited[i] = true;
        List<Edge> edges = g.get(i);
        for (Edge e : edges) {
            if (!visited[e.to()]) {
                dfs(g, e.to(), sort, visited);
            }
        }
        sort.add(i);
    }

    private void dfs(List<List<Edge>> g, int i, Float[] dist, int[] prev) {
        List<Edge> outs = g.get(i);
        for (Edge edge : outs) {
            if (dist[edge.from()] + edge.weight() < dist[edge.to()]) {
                dist[edge.to()] = dist[edge.from()] + (float) edge.weight();
                prev[edge.to()] = edge.from();
            }
            dfs(g, edge.to(), dist, prev);
        }
    }

}
