package com.backflip.vadsh.ds.graph.task;

import com.backflip.vadsh.ds.graph.Config;
import com.backflip.vadsh.ds.graph.Graph;

import java.util.*;

import static com.backflip.vadsh.ds.graph.task.TaskResult.successNodes;

public class FindArticulationPoints implements Task {

    @Override
    public TaskResult execute(Graph graph, Config config, Map<String, Integer> params) {

        List<List<Integer>> g = graph.adjacencyList();
        int n = graph.n();

        int[] visited = new int[n];
        for (int i = 0; i < n; i++) visited[i] = -1;

        int[] llv = new int[n];
        for (int i = 0; i < n; i++) llv[i] = -1;

        Set<Integer> articulationPoints = new HashSet<>();

        Counter c = new Counter();

        for (int i = 0; i < n; i++) {
            if (visited[i] != -1) continue;
            dfs(g, i, -1, visited, llv, c, articulationPoints);
        }

        return successNodes(new ArrayList<>(articulationPoints));
    }

    private void dfs(List<List<Integer>> g, int from, int parent, int[] visited, int[] llv, Counter c, Set<Integer> articulationPoints) {
        if (visited[from] != -1) return;

        int currRef = c.inc();
        visited[from] = currRef;
        llv[from] = currRef;

        for (Integer to : g.get(from)) {
            if (to == parent) continue;
            dfs(g, to, from, visited, llv, c, articulationPoints);

            if (llv[to] < llv[from]) llv[from] = llv[to];
            else if (llv[to] > visited[from]) {
                if (g.get(from).size() > 1) articulationPoints.add(from);
                if (g.get(to).size() > 1) articulationPoints.add(to);
            } else if (visited[from] == llv[to] && parent != -1 && llv[parent] < llv[from]) {
                articulationPoints.add(from);
            }
        }

    }

    private static class Counter {
        private int c = 0;

        int inc() {
            return c++;
        }
    }

}
