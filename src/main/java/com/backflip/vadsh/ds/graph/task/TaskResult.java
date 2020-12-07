package com.backflip.vadsh.ds.graph.task;

import com.backflip.vadsh.ds.graph.Edge;

import java.util.Collections;
import java.util.List;

public abstract class TaskResult {

    protected boolean success;

    public boolean failed() {
        return !success;
    }

    public static TaskResult success(List<Integer> nodes, List<Edge> edges) {
        nodes = nodes == null ? Collections.emptyList() : nodes;
        edges = edges == null ? Collections.emptyList() : edges;
        return new TaskExecutionSuccess(nodes, edges);
    }

    public static TaskResult success() {
        return new TaskExecutionSuccess(Collections.emptyList(), Collections.emptyList());
    }

    public static TaskResult failure(String message) {
        return new TaskExecutionFailed(message);
    }

}
