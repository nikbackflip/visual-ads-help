package com.backflip.vadsh.ds.graph.analyzer;

import com.backflip.vadsh.ds.graph.Config;
import com.backflip.vadsh.ds.graph.Graph;

import java.util.List;

public class DisconnectedAnalyzer implements Analyzer {

    @Override
    public boolean analyze(Graph graph, Config config) {
        List<List<Integer>> g = graph.adjacencyList();
        int n = graph.n();
        if (n == 0) return false;

        for (int j = 0; j < n; j++) {
            boolean[] visited = new boolean[n];
            for (int i = 0; i < n; i ++) {
                visited[i] = false;
            }
            visited[j] = true;

            dfs(j, g, visited);

            boolean iterationConnected = true;
            for (int i = 0; i < n; i ++) {
                if (!visited[i]) {
                    iterationConnected = false;
                    break;
                }
            }
            if (iterationConnected) return false;
        }
        return true;
    }

    private void dfs(int node, List<List<Integer>> graph, boolean[] visited) {
        List<Integer> listTo = graph.get(node);
        for (Integer to: listTo) {
            if (!visited[to]) {
                visited[to] = true;
                dfs(to, graph, visited);
            }
        }
    }
}
