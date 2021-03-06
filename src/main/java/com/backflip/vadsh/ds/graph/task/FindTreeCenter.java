package com.backflip.vadsh.ds.graph.task;

import com.backflip.vadsh.ds.graph.Config;
import com.backflip.vadsh.ds.graph.Graph;

import java.util.*;
import java.util.stream.IntStream;

import static com.backflip.vadsh.ds.graph.task.TaskResult.success;
import static com.backflip.vadsh.ds.graph.task.TaskResult.successNodes;

public class FindTreeCenter implements Task {

    @Override
    public TaskResult execute(Graph graphInput, Config config, Map<String, Integer> params) {
        List<List<Integer>> graph = graphInput.adjacencyList();
        int n = graphInput.n();

        if (n == 0) {
            return success();
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
            for (Integer leaf : leafs) {
                Integer parent = graph.get(leaf).get(0);
                remainingNodes.remove(leaf);
                graph.get(leaf).removeIf(v -> v.equals(parent));
                graph.get(parent).removeIf(v -> v.equals(leaf));
            }
        }
        return successNodes(new ArrayList<>(remainingNodes));
    }
}
