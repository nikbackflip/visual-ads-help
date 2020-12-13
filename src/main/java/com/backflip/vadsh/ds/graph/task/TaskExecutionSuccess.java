package com.backflip.vadsh.ds.graph.task;

import com.backflip.vadsh.ds.graph.Edge;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Set;

@Builder
@Getter
public class TaskExecutionSuccess extends TaskResult {

    public TaskExecutionSuccess(List<Integer> nodes, List<Edge> edges, List<Set<Integer>> components) {
        this.nodes = nodes;
        this.edges = edges;
        this.components = components;
        this.success = true;
    }

    private final List<Integer> nodes;
    private final List<Edge> edges;
    private final List<Set<Integer>> components;

}
