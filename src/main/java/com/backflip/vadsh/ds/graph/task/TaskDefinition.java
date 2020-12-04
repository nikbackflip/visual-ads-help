package com.backflip.vadsh.ds.graph.task;


import com.backflip.vadsh.ds.graph.Config;
import com.backflip.vadsh.ds.graph.Graph;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;

import static java.util.Collections.*;

@Getter
@AllArgsConstructor
public enum TaskDefinition {

    FIND_TREE_CERNER(
            "Find Tree Center",
            Constants.TREE_CENTER_ID,
            emptyList(),
            new FindTreeCenter()
    ),
    FIND_SHORTEST_PATH(
            "Find Shortest Path",
            Constants.SHORTEST_PATH_ID,
            List.of(TaskParameter.of("from", "From Node"), TaskParameter.of("to", "To Node")),
            new FindShortestPath()
    ),
    FIND_LONGEST_PATH(
            "Find Longest Path",
            Constants.LONGEST_PATH_ID,
            List.of(TaskParameter.of("from", "From Node"), TaskParameter.of("to", "To Node")),
            new FindLongestPath()
    ),
    FIND_MINIMUM_SPANNING_TREE(
            "Find Minimum Spanning Tree",
            Constants.MINIMUM_SPANNING_TREE_ID,
            emptyList(),
            new FindMinimumSpanningTree()
    );

    private final String label;
    private final String id;
    private final List<TaskParameter> taskParameters;
    private final Task task;

    public static TaskDefinition fromId(String id) {
        switch (id) {
            case Constants.TREE_CENTER_ID:
                return FIND_TREE_CERNER;
            case Constants.SHORTEST_PATH_ID:
                return FIND_SHORTEST_PATH;
            case Constants.LONGEST_PATH_ID:
                return FIND_LONGEST_PATH;
            case Constants.MINIMUM_SPANNING_TREE_ID:
                return FIND_MINIMUM_SPANNING_TREE;
            default:
                throw new IllegalStateException("Unexpected value: " + id);
        }
    }

    public TaskResponse execute(Graph graph, Config config, Map<String, Integer> params) {
        return task.execute(graph, config, params);
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

    private static final class Constants {
        private static final String TREE_CENTER_ID = "treeCenter";
        private static final String SHORTEST_PATH_ID = "shortestPath";
        private static final String LONGEST_PATH_ID = "longestPath";
        private static final String MINIMUM_SPANNING_TREE_ID = "minimumSpanningTree";
    }
}


