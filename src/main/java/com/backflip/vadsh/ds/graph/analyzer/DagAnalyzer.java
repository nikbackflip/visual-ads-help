package com.backflip.vadsh.ds.graph.analyzer;

import com.backflip.vadsh.ds.graph.Config;
import com.backflip.vadsh.ds.graph.Graph;

import java.util.List;

public class DagAnalyzer implements Analyzer {

    @Override
    public boolean analyze(Graph graph, Config config) {
        if (!config.isGraphDirectional()) return false;

        int n = graph.n();
        List<List<Integer>> graphList = graph.adjacencyList();

        for (int i = 0; i < n; i++) {
            boolean[] visited = new boolean[n];
            if (!dfs(i, graphList, visited)) return false;
        }

        return true;
    }

    private boolean dfs(Integer root, List<List<Integer>> graph, boolean[] visited) {
        visited[root] = true;

        List<Integer> children = graph.get(root);
        for (Integer child : children) {
            if (visited[child] || !dfs(child, graph, visited)) return false;
        }

        visited[root] = false;
        return true;
    }

}
