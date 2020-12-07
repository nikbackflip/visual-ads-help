package com.backflip.vadsh.ds.graph.task;

import com.backflip.vadsh.ds.graph.Config;
import com.backflip.vadsh.ds.graph.Graph;
import com.backflip.vadsh.ds.graph.analyzer.AnalyticDefinition;

import java.util.*;
import java.util.stream.IntStream;

public class FindTreeCenter implements Task {

    @Override
    public TaskResult execute(Graph graphInput, Config config, Map<String, Integer> params) {
        List<List<Integer>> graph = graphInput.adjacencyList();
        int n = graphInput.n();

        if (n == 0) {
            return TaskResult.success();
        }

        Set<Integer> remainingNodes = new HashSet<>();
        IntStream.range(0, n).forEach(remainingNodes::add);

        while (remainingNodes.size() != 1 && remainingNodes.size() != 2) {
            List<Integer> leafs = new LinkedList<>();
            for (int i = 0; i < n; i++) {
                if (graph.get(i).size() == 1) {
                    leafs.add(i);
                }
            }
            for(Integer leaf: leafs) {
                Integer parent = graph.get(leaf).get(0);
                remainingNodes.remove(leaf);
                graph.get(leaf).removeIf(v -> v.equals(parent));
                graph.get(parent).removeIf(v -> v.equals(leaf));
            }
        }
        return TaskResult.success(new ArrayList<>(remainingNodes), Collections.emptyList());
    }

    @Override
    public boolean paramsValid(Graph graph, Config config, Map<String, Integer> params) {
        return params.isEmpty();
    }

    @Override
    public boolean executionPossible(Graph graph, Config config) {
        return AnalyticDefinition.TREE.analyze(graph, config);
    }
}
