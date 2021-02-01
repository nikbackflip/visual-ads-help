package com.backflip.vadsh.ds.graph.task;

import com.backflip.vadsh.ds.graph.Config;
import com.backflip.vadsh.ds.graph.Edge;
import com.backflip.vadsh.ds.graph.Graph;

import java.util.*;

import static com.backflip.vadsh.ds.graph.task.TaskResult.*;

public class FindEulerianPath implements Task {

    @Override
    public TaskResult execute(Graph graph, Config config, Map<String, Integer> params) {

        List<Edge> edges = graph.edgeList();
        int n = graph.n();

        if (edges.size() == 0) return success();

        int[] in = new int[n];
        int[] out = new int[n];

        for (Edge ed: edges) {
            out[ed.from()]++;
            in[ed.to()]++;
        }

        int startNode = -1;
        for (int i = 0; i < n; i++) {
            if (out[i] - in[i] == 1) {
                startNode = i; break;
            }
            if (startNode == -1) startNode = 0;
        }

        List<Integer> path = new LinkedList<>();
        dfs(graph.adjacencyList(), startNode, path, out);

        Collections.reverse(path);

        List<Edge> result = new LinkedList<>();
        for (int i = 0; i < path.size() - 1; i++) {
            result.add(new Edge(path.get(i), path.get(i+1), 1.0));
        }

        return successEdges(result);
    }

    private void dfs(List<List<Integer>> g, int from, List<Integer> path, int[] out) {
        List<Integer> children = g.get(from);
        for (int i = children.size() - out[from]; i < children.size(); i++) {
            out[from]--;
            dfs(g, children.get(i), path, out);
            if (out[from] == 0) break;
        }
        if (out[from] == 0) {
            path.add(from);
        }
    }
}
