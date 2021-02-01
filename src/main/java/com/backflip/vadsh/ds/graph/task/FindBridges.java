package com.backflip.vadsh.ds.graph.task;

import com.backflip.vadsh.ds.graph.Config;
import com.backflip.vadsh.ds.graph.Edge;
import com.backflip.vadsh.ds.graph.Graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.backflip.vadsh.ds.graph.task.TaskResult.successEdges;

public class FindBridges implements Task {

    @Override
    public TaskResult execute(Graph graph, Config config, Map<String, Integer> params) {

        List<List<Integer>> g = graph.adjacencyList();
        int n = graph.n();

        int[] llv = new int[n];
        for (int i = 0; i < n; i++) llv[i] = -1;

        int[] visited = new int[n];
        for (int i = 0; i < n; i++) visited[i] = -1;

        List<Edge> bridges = new ArrayList<>();

        Counter c = new Counter();
        for (int i = 0; i < n; i++) {
            if (visited[i] != -1) continue;
            dfs(g, i, -1, visited, llv, c, bridges);
        }

        return successEdges(bridges);
    }

    private void dfs(List<List<Integer>> g, int from, int parent, int[] visited, int[] llv, Counter c, List<Edge> bridges) {
        if (visited[from] != -1) return;

        int currRef = c.inc();
        visited[from] = currRef;
        llv[from] = currRef;

        for (Integer to : g.get(from)) {
            if (to == parent) continue;
            dfs(g, to, from, visited, llv, c, bridges);
            if (llv[to] < llv[from]) llv[from] = llv[to];
            else if (llv[to] > visited[from]) bridges.add(new Edge(from, to, 1.0));
        }
    }


    private static class Counter {
        int c = 0;

        int inc() {
            return c++;
        }
    }
}
