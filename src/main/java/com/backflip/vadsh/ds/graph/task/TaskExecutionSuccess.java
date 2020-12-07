package com.backflip.vadsh.ds.graph.task;

import com.backflip.vadsh.ds.graph.Edge;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class TaskExecutionSuccess extends TaskResult {

    public TaskExecutionSuccess(List<Integer> nodes, List<Edge> edges) {
        this.nodes = nodes;
        this.edges = edges;
        this.success = true;
    }

    private final List<Integer> nodes;
    private final List<Edge> edges;

}
