package com.backflip.vadsh.ds.graph.task;


import com.backflip.vadsh.ds.graph.Config;
import com.backflip.vadsh.ds.graph.Graph;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;

import static com.backflip.vadsh.ds.graph.analyzer.AnalyticDefinition.*;
import static com.backflip.vadsh.ds.graph.task.TaskResult.failure;
import static java.lang.String.format;
import static java.util.Collections.*;

@Getter
@AllArgsConstructor
public enum TaskDefinition {

    FIND_TREE_CERNER(
            "Find Tree Center",
            Constants.TREE_CENTER_ID,
            emptyList(),
            List.of(
                    TaskPrerequisite.of((g, c) -> !DIRECTIONAL.analyze(g, c), "Graph must be undirected"),
                    TaskPrerequisite.of(TREE::analyze, "Graph must be a tree")
            ),
            new FindTreeCenter()
    ),
    FIND_SHORTEST_PATH_DIJKSTRAS(
            "Find Shortest Path - Dijkstra's",
            Constants.DIJKSTRAS_SHORTEST_PATH_ID,
            List.of(TaskParameter.of("from", "From Node"), TaskParameter.of("to", "To Node")),
            List.of(TaskPrerequisite.of((g, c) -> !NEGATIVE_CYCLES.analyze(g, c), "Graph must not contain negative cycles")),
            new DijkstrasFindShortestPath()
    ),
    FIND_SHORTEST_PATH_BELLMAN_FORD(
            "Find Shortest Path - Bellman-Ford",
            Constants.BELLMAN_FORD_SHORTEST_PATH_ID,
            List.of(TaskParameter.of("from", "From Node"), TaskParameter.of("to", "To Node")),
            emptyList(),
            new BellmanFordFindShortestPath()
    ),
    FIND_MINIMUM_SPANNING_TREE(
            "Find Minimum Spanning Tree",
            Constants.MINIMUM_SPANNING_TREE_ID,
            emptyList(),
            List.of(TaskPrerequisite.of((g, c) -> !DISCONNECTED.analyze(g, c), "Graph must not contain disconnected components")),
            new FindMinimumSpanningTree()
    ),
    FIND_STRONGLY_CONNECTED_COMPONENTS(
            "Find Strongly Connected Components",
            Constants.STRONGLY_CONNECTED_COMPONENTS_ID,
            emptyList(),
            List.of(TaskPrerequisite.of(DIRECTIONAL::analyze, "Graph must be directional")),
            new FindStronglyConnectedComponents()
    ),
    FIND_BRIDGES(
            "Find Bridges",
            Constants.BRIDGES_ID,
            emptyList(),
            List.of(TaskPrerequisite.of((g, c) -> !DIRECTIONAL.analyze(g, c), "Graph must be undirected")),
            new FindBridges()
    );

    private final String label;
    private final String id;
    private final List<TaskParameter> taskParameters;
    private final List<TaskPrerequisite> prerequisites;
    private final Task task;

    public static TaskDefinition fromId(String id) {
        switch (id) {
            case Constants.TREE_CENTER_ID:
                return FIND_TREE_CERNER;
            case Constants.DIJKSTRAS_SHORTEST_PATH_ID:
                return FIND_SHORTEST_PATH_DIJKSTRAS;
            case Constants.BELLMAN_FORD_SHORTEST_PATH_ID:
                return FIND_SHORTEST_PATH_BELLMAN_FORD;
            case Constants.MINIMUM_SPANNING_TREE_ID:
                return FIND_MINIMUM_SPANNING_TREE;
            case Constants.STRONGLY_CONNECTED_COMPONENTS_ID:
                return FIND_STRONGLY_CONNECTED_COMPONENTS;
            case Constants.BRIDGES_ID:
                return FIND_BRIDGES;
            default:
                throw new IllegalStateException("Unexpected value: " + id);
        }
    }

    public TaskResult execute(Graph graph, Config config, Map<String, Integer> params) {
        Validation paramsValidation = validateParams(params, graph.n());
        if (paramsValidation.failed()) return failure(paramsValidation.getMessage());

        Validation prerequisitesValidation = validatePrerequisites(graph, config);
        if (prerequisitesValidation.failed()) return failure(prerequisitesValidation.getMessage());

        return task.execute(graph, config, params);
    }

    private Validation validateParams(Map<String, Integer> params, int graphSize) {
        if (taskParameters.size() != params.size()) {
            return new Validation(false, "Wrong parameters number");
        }
        for (TaskParameter expectedParam: taskParameters) {
            Integer value = params.get(expectedParam.id);
            if (value == null) return new Validation(false, format("Parameter %s absent", expectedParam.label));
            if (value < 0 || value >= graphSize) return new Validation(false, format("Parameter %s is invalid", expectedParam.label));
        }
        return new Validation(true, "success");
    }

    private Validation validatePrerequisites(Graph graph, Config config) {
        for (TaskPrerequisite prerequisite: prerequisites) {
            if (!prerequisite.condition.test(graph, config)) return new Validation(false, prerequisite.getFailureMessage());
        }
        return new Validation(true, "success");
    }

    @Getter
    @AllArgsConstructor
    public static class TaskParameter {
        private final String id;
        private final String label;

        public static TaskParameter of(String id, String label) {
            return new TaskParameter(id, label);
        }
    }

    @AllArgsConstructor
    private static class Validation {
        private final boolean success;
        private final String message;

        boolean failed() {
            return !success;
        }

        String getMessage() {
            return message;
        }
    }

    @Getter
    @AllArgsConstructor
    private static class TaskPrerequisite {
        private final BiPredicate<Graph, Config> condition;
        private final String failureMessage;

        public static TaskPrerequisite of(BiPredicate<Graph, Config> condition, String failureMessage) {
            return new TaskPrerequisite(condition, failureMessage);
        }
    }

    private static final class Constants {
        private static final String STRONGLY_CONNECTED_COMPONENTS_ID = "stronglyConnectedComponents";
        private static final String TREE_CENTER_ID = "treeCenter";
        private static final String DIJKSTRAS_SHORTEST_PATH_ID = "shortestPathDijkstras";
        private static final String BELLMAN_FORD_SHORTEST_PATH_ID = "shortestPathBellmanFord";
        private static final String MINIMUM_SPANNING_TREE_ID = "minimumSpanningTree";
        private static final String BRIDGES_ID = "bridges";
    }
}


