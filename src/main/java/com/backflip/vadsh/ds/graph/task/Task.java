package com.backflip.vadsh.ds.graph.task;

import com.backflip.vadsh.ds.graph.Config;
import com.backflip.vadsh.ds.graph.Graph;

import java.util.Map;

public interface Task {

    TaskResult execute(Graph graph, Config config, Map<String, Integer> params);

    boolean paramsValid(Graph graph, Config config, Map<String, Integer> params);

    boolean executionPossible(Graph graph, Config config);
}
