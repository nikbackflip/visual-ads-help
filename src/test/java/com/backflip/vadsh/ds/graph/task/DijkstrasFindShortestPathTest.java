package com.backflip.vadsh.ds.graph.task;

import com.backflip.vadsh.ds.graph.Config;
import com.backflip.vadsh.ds.graph.Edge;
import com.backflip.vadsh.ds.graph.Graph;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DijkstrasFindShortestPathTest {

    @ParameterizedTest
    @MethodSource("input")
    public void taskTest(Graph graph, Map<String, Integer> params, List<Edge> expected) {
        //given
        Task task = new DijkstrasFindShortestPath();

        //when
        TaskResult result = task.execute(graph, config, params);

        //then
        assertEquals(expected, ((TaskExecutionSuccess)result).getEdges());
    }

    private final static Config config = new Config(true, true, false);
    private final static Graph twoNodesGraph = new Graph(List.of(new Edge(0, 1, 1.0), new Edge(1, 0, 1.0)), 2);
    private final static Graph circle = new Graph(List.of(new Edge(0, 1, 1.0), new Edge(1, 2, 1.0), new Edge(2, 3, 1.0), new Edge(3, 4, 1.0), new Edge(4, 0, 1.0)), 5);
    private final static Graph circleShortcut = new Graph(List.of(new Edge(0, 1, 1.0), new Edge(1, 2, 1.0), new Edge(2, 3, 1.0), new Edge(3, 4, 1.0), new Edge(4, 0, 1.0), new Edge(1, 4, 1.0)), 5);
    private final static Graph weightedGraph = new Graph(List.of(new Edge(14, 13, 6.0), new Edge(13, 0, 1.0), new Edge(0, 4, 1.0), new Edge(4, 3, 1.0), new Edge(3, 19, 1.0), new Edge(1, 4, 1.0), new Edge(4, 1, 1.0), new Edge(4, 12, 5.0), new Edge(12, 0, 1.0), new Edge(0, 5, 1.0), new Edge(5, 0, 1.0), new Edge(5, 1, 1.0), new Edge(1, 15, 1.0), new Edge(15, 8, 1.0), new Edge(8, 3, 1.0), new Edge(3, 12, 1.0), new Edge(12, 19, 1.0), new Edge(19, 12, 1.0), new Edge(19, 18, 1.0), new Edge(18, 11, 1.0), new Edge(11, 3, 16.0), new Edge(3, 2, 12.0), new Edge(2, 7, 4.0), new Edge(7, 2, 1.0), new Edge(7, 9, 4.0), new Edge(9, 10, 1.0), new Edge(10, 6, 1.0), new Edge(6, 8, 1.0), new Edge(17, 8, 1.0), new Edge(8, 9, 2.0), new Edge(9, 16, 10.0), new Edge(16, 15, 4.0), new Edge(15, 16, 8.0), new Edge(19, 10, 10.0), new Edge(10, 19, 11.0), new Edge(17, 10, 1.0), new Edge(10, 17, 1.0)), 20);

    private static Stream<Arguments> input() {
        return Stream.of(
                Arguments.of(twoNodesGraph, Map.of("from", 0, "to", 1), List.of(new Edge(0, 1, 1.0))),
                Arguments.of(circle, Map.of("from", 1, "to", 4), List.of(new Edge(1, 2, 1.0), new Edge(2, 3, 1.0), new Edge(3, 4, 1.0))),
                Arguments.of(circle, Map.of("from", 1, "to", 0), List.of(new Edge(1, 2, 1.0), new Edge(2, 3, 1.0), new Edge(3, 4, 1.0), new Edge(4, 0, 1.0))),
                Arguments.of(circleShortcut, Map.of("from", 1, "to", 0), List.of(new Edge(1, 4, 1.0), new Edge(4, 0, 1.0))),
                Arguments.of(weightedGraph, Map.of("from", 0, "to", 19), List.of(new Edge(0, 4, 1.0), new Edge(4, 3, 1.0), new Edge(3, 19, 1.0))),
                Arguments.of(weightedGraph, Map.of("from", 16, "to", 12), List.of(new Edge(16, 15, 1.0), new Edge(15, 8, 1.0), new Edge(8, 3, 1.0), new Edge(3, 12, 1.0)))
        );
    }
}

