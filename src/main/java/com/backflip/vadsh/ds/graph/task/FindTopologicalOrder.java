package com.backflip.vadsh.ds.graph.task;

import com.backflip.vadsh.ds.graph.Config;
import com.backflip.vadsh.ds.graph.Edge;
import com.backflip.vadsh.ds.graph.Graph;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.backflip.vadsh.ds.graph.task.TaskResult.successNodes;

public class FindTopologicalOrder implements Task {

    @Override
    public TaskResult execute(Graph graph, Config config, Map<String, Integer> params) {
        List<List<Edge>> g = graph.adjacencyListOfEdges();
        int n = graph.n();

        boolean[] visited = new boolean[n];
        List<Integer> order = new LinkedList<>();

        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                dfs(g, i, visited, order);
            }
        }

        Collections.reverse(order);
        return successNodes(order);
    }

    private void dfs(List<List<Edge>> g, int i, boolean[] visited, List<Integer> order) {
        visited[i] = true;
        for (Edge e : g.get(i)) {
            if (!visited[e.to()]) {
                dfs(g, e.to(), visited, order);
            }
        }
        order.add(i);
    }

}
