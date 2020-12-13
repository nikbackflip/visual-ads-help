package com.backflip.vadsh.ds.graph.task;

import com.backflip.vadsh.ds.graph.Config;
import com.backflip.vadsh.ds.graph.Edge;
import com.backflip.vadsh.ds.graph.Graph;

import java.util.*;

import static java.util.Collections.*;

public class FindBridges implements Task {

    @Override
    public TaskResult execute(Graph graph, Config config, Map<String, Integer> params) {

        List<List<Integer>> g = graph.adjacencyList();
        int n = graph.n();

        int[] llv = new int[n];
        for (int i = 0; i < n; i++) llv[i] = -1;

        int[] visited = new int[n];
        for (int i = 0; i < n; i++) visited[i] = -1;

        Map<Integer, Set<Integer>> excludedEdges = new HashMap<>();
        for (int i = 0; i < n; i++) {
            excludedEdges.put(i, new HashSet<>());
        }

        int next = getNotVisited(visited);
        while (next != -1) {
            dfs(g, next, new Counter(), visited, llv, excludedEdges);
            next = getNotVisited(visited);
        }

        List<Edge> bridges = new ArrayList<>();
        for(Edge e: graph.edgeList()) {
            if (llv[visited[e.from()]] < llv[visited[e.to()]])
                bridges.add(e);
        }

        return TaskResult.success(emptyList(), bridges);
    }

    private int getNotVisited(int[] visited) {
        for (int i = 0; i < visited.length; i++) {
            if (visited[i] == -1) return i;
        }
        return -1;
    }

    private void dfs(List<List<Integer>> g, int current, Counter counter, int[] visited, int[] llv, Map<Integer, Set<Integer>> excludedEdges) {
        if (visited[current] != -1) return;

        visited[current] = counter.inc();
        int currRef = visited[current];
        llv[currRef] = currRef;

        for (Integer child: g.get(current)) {
            if (excludedEdges.get(current).contains(child)) continue;
            excludedEdges.get(child).add(current);
            dfs(g, child, counter, visited, llv, excludedEdges);
            if (llv[visited[child]] < llv[currRef]) llv[currRef] = llv[visited[child]];
        }
    }

    static class Counter {
        int c = 0;
        int inc() {
            return c++;
        }
    }
}
