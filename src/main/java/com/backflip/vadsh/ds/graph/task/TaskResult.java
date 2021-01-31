package com.backflip.vadsh.ds.graph.task;

import com.backflip.vadsh.ds.graph.Edge;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public abstract class TaskResult {

    protected boolean success;

    public boolean failed() {
        return !success;
    }

    public static TaskResult success(List<Integer> nodes, List<Edge> edges) {
        nodes = nodes == null ? Collections.emptyList() : nodes;
        edges = edges == null ? Collections.emptyList() : edges;
        return new TaskExecutionSuccess(nodes, edges, Collections.emptyList());
    }

    public static TaskResult success(List<Set<Integer>> components) {
        components = components == null ? Collections.emptyList() : components;
        return new TaskExecutionSuccess(Collections.emptyList(), Collections.emptyList(), components);
    }

    public static TaskResult success() {
        return new TaskExecutionSuccess(Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
    }

    public static TaskResult failure(String message) {
        return new TaskExecutionFailed(message);
    }

}
