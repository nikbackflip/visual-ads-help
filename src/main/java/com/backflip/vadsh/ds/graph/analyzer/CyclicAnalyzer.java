package com.backflip.vadsh.ds.graph.analyzer;

import com.backflip.vadsh.ds.graph.Config;
import com.backflip.vadsh.ds.graph.Graph;

import java.util.List;

public class CyclicAnalyzer implements Analyzer {

    @Override
    public boolean analyze(Graph graph, Config config) {

        List<List<Integer>> g = graph.adjacencyList();
        int n = graph.n();

        for (int i = 0; i < n; i++) {
            boolean[] visited = new boolean[n];
            visited[i] = true;
            boolean cycleDetected = dfs(-1, i, visited, g, config.isGraphDirectional());
            if (cycleDetected) return true;
        }
        return false;
    }

    private boolean dfs(int prev, int current, boolean[] visited, List<List<Integer>> g, boolean graphDirectional) {
        for (Integer i: g.get(current)) {
            if (visited[i]) {
                if (i == prev && !graphDirectional) continue;
                return true;
            }
            visited[i] = true;

            if (dfs(current, i, visited, g, graphDirectional)) return true;
            visited[i] = false;
        }
        return false;
    }
}
