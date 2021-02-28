package com.backflip.vadsh.ds.graph.layout;

import com.backflip.vadsh.ds.graph.Config;
import com.backflip.vadsh.ds.graph.Graph;
import com.backflip.vadsh.ds.graph.task.TaskDefinition;
import com.backflip.vadsh.ds.graph.task.TaskExecutionSuccess;

import java.util.*;
import java.util.stream.IntStream;

import static java.util.Collections.emptyMap;
import static java.util.stream.Collectors.toMap;

public class DagLayout implements Layout {

    private final Graph graph;
    private final Config config;
    private int[] lines;
    private List<Integer> order;
    private List<List<Integer>> children;
    private int lastLine = -1;
    private final int gridX;
    private final int gridY;
    private final int a = 60; //square size


    public DagLayout(Graph graph, Config config, int x, int y) {
        this.gridX = x / a;
        this.gridY = y / a;
        this.graph = graph;
        this.config = config;
    }

    @Override
    public Map<Integer, Coordinates> layout() {
        int n = graph.n();
        if (n > gridX && n > gridY) return null;
        boolean horizontalLayout = gridX >= n;

        order = ((TaskExecutionSuccess)TaskDefinition.FIND_TOPOLOGICAL_ORDER.execute(graph, config, emptyMap())).getNodes();
        children = graph.adjacencyList();

        lines = new int[n];
        for (int i = 0; i < n; i++) lines[i] = -1;

        Collections.reverse(order);
        order.forEach(this::putInLine);
        Collections.reverse(order);

        int maxScatter = horizontalLayout ? (gridY - 1) / 2 : (gridX - 1) / 2;
        int zeroLine = horizontalLayout ? gridY / 2 : gridX / 2;
        return IntStream.range(0, lines.length)
                .boxed()
                .collect(toMap(i -> i, i -> (lines[i] % 2 == 0 ? (lines[i] / 2) * -1 : (int)Math.ceil((double)lines[i] / 2))))
                .entrySet().stream()
                .peek(e -> {
                    int relativeLine = e.getValue();
                    if (Math.abs(relativeLine) > maxScatter) {
                        e.setValue(relativeLine > 0 ? maxScatter : maxScatter * -1);
                    }
                })
                .peek(e -> e.setValue(e.getValue() +  + zeroLine))
                .collect(toMap(e -> order.get(e.getKey()), e -> {
                    if (horizontalLayout) return new Coordinates(a * e.getKey() + a / 2, a * e.getValue() + a / 2);
                    else return new Coordinates(a * e.getValue() + a / 2, a * e.getKey() + a / 2);
                }));
    }

    private void putInLine(int node) {
        Map<Integer, Integer> childToLineMap = children.get(node).stream()
                .collect(toMap(i -> i, i -> lines[i]));

        int line = childToLineMap.entrySet().stream()
                .min(Comparator.comparingInt(e0 -> order.indexOf(e0.getKey())))
                .orElse(Map.entry(-1, -1)).getValue();

        lines[node] = line == -1 ? ++lastLine : line;
    }
}
