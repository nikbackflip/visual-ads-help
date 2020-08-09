package com.backflip.vadsh.ds.graph;

import java.util.List;

public class GraphAnalyzer {

    public static boolean complete(Graph graph, Boolean selfLoopsAllowed) {
        if (selfLoopsAllowed) {
            return graph.edgeList().size() == graph.n() * graph.n();
        } else {
            return graph.edgeList().size() == graph.n() * (graph.n() - 1);
        }
    }

    public static boolean tree(Graph graphObject, boolean graphDirectional) {
        int n = graphObject.n();
        List<List<Integer>> graph = graphObject.adjacencyList();


        for (int i = 0; i < n; i++) {
            boolean[] visited = new boolean[n];


            boolean noCycles = graphDirectional ? dfs(i, graph, visited) : dfs(i, null, graph, visited);
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

    private static boolean dfs(Integer root, List<List<Integer>> graph, boolean[] visited) {

        visited[root] = true;

        List<Integer> children = graph.get(root);
        for (Integer child : children) {
            if (visited[child] || !dfs(child, graph, visited)) return false;
        }

        return true;
    }

    private static boolean dfs(Integer root, Integer parent, List<List<Integer>> graph, boolean[] visited) {

        visited[root] = true;

        List<Integer> children = graph.get(root);
        for (Integer child : children) {
            if (child.equals(parent)) continue;
            if (visited[child] || !dfs(child, root, graph, visited)) return false;
        }

        return true;
    }


}
