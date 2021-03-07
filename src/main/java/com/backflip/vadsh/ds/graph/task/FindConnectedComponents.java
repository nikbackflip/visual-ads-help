package com.backflip.vadsh.ds.graph.task;

import com.backflip.vadsh.ds.graph.Config;
import com.backflip.vadsh.ds.graph.Graph;

import java.util.*;
import java.util.stream.Collectors;

public class FindConnectedComponents implements Task {

    @Override
    public TaskResult execute(Graph graph, Config config, Map<String, Integer> params) {

        List<List<Integer>> g = graph.adjacencyList();
        int n = graph.n();

        int[] visited = new int[n];
        int component = 1;

        for (int i = 0; i < n; i++ ) {
            if (visited[i] == 0) {
                dfs(g, i, visited, component);
                component++;
            }
        }

        Map<Integer, Set<Integer>> cc = new HashMap<>();
        for (int i = 1; i < component; i++) {
            cc.put(i, new HashSet<>());
        }
        for (int i = 0; i < n; i++) {
            int c = visited[i];
            cc.get(c).add(i);
        }
        return TaskResult.success(cc.values().stream().filter((s) -> !s.isEmpty()).collect(Collectors.toList()));
    }

    private void dfs(List<List<Integer>> g, int current, int[] visited, int component) {
        if (visited[current] == 0) {
            visited[current] = component;
        } else {
            if (visited[current] != component) {
                int oldComponent = visited[current];
                for (int i = 0; i < g.size(); i++) {
                    if (visited[i] == oldComponent) {
                        visited[i] = component;
                    }
                }
                visited[current] = component;
            } else {
                return;
            }
        }
        List<Integer> children = g.get(current);
        for (Integer child : children) {
            dfs(g, child, visited, component);
        }
    }

}