package com.backflip.vadsh.ds.graph.analyzer;

import com.backflip.vadsh.ds.graph.Config;
import com.backflip.vadsh.ds.graph.Graph;

import java.util.List;

public class TreeAnalyzer implements Analyzer {

    @Override
    public boolean analyze(Graph graph, Config config) {
        int n = graph.n();
        List<List<Integer>> graphList = graph.adjacencyList();

        for (int i = 0; i < n; i++) {
            boolean[] visited = new boolean[n];

            boolean noCycles = config.isGraphDirectional() ? dfs(i, graphList, visited) : dfs(i, null, graphList, visited);
            boolean connected = true;
            for (int j = 0; j < n; j++) {
                if (!visited[j]) {
                    connected = false;
                    break;
                }
            }
            if (noCycles && connected) return true;
        }

        return false;
    }

    private boolean dfs(Integer root, List<List<Integer>> graph, boolean[] visited) {
        visited[root] = true;

        List<Integer> children = graph.get(root);
        for (Integer child : children) {
            if (visited[child] || !dfs(child, graph, visited)) return false;
        }

        return true;
    }

    private boolean dfs(Integer root, Integer parent, List<List<Integer>> graph, boolean[] visited) {
        visited[root] = true;

        List<Integer> children = graph.get(root);
        for (Integer child : children) {
            if (child.equals(parent)) continue;
            if (visited[child] || !dfs(child, root, graph, visited)) return false;
        }

        return true;
    }

}
