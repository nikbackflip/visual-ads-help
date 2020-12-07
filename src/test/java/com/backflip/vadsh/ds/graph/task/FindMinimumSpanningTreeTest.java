package com.backflip.vadsh.ds.graph.task;

import com.backflip.vadsh.ds.graph.Config;
import com.backflip.vadsh.ds.graph.Edge;
import com.backflip.vadsh.ds.graph.Graph;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FindMinimumSpanningTreeTest {

    @ParameterizedTest
    @MethodSource("input")
    public void taskTest(Graph graph, List<Edge> expected) {
        //given
        Task task = new FindMinimumSpanningTree();

        //when
        TaskResult result = task.execute(graph, notDirectionalConfig, Collections.emptyMap());

        //then
        assertEquals(expected, ((TaskExecutionSuccess)result).getEdges());
    }

    private final static Config notDirectionalConfig = new Config(false, true, true);
    private final static Graph emptyGraph = new Graph(List.of(), 0);
    private final static Graph oneNodeGraph = new Graph(List.of(), 1);
    private final static Graph twoNodesGraph = new Graph(List.of(new Edge(0, 1, 1.0), new Edge(1, 0, 1.0)), 2);
    private final static Graph triangleGraph = new Graph(List.of(new Edge(0, 1, 1.0), new Edge(1, 0, 1.0), new Edge(1, 2, 1.0), new Edge(2, 1, 1.0), new Edge(2, 0, 1.0), new Edge(0, 2, 1.0)), 3);
    private final static Graph completeGraph = new Graph(List.of(new Edge(0, 1, 1.0), new Edge(1, 0, 1.0),new Edge(1, 3, 1.0),new Edge(3, 1, 1.0),new Edge(3, 0, 1.0),new Edge(0, 3, 1.0),new Edge(0, 6, 1.0),new Edge(6, 0, 1.0),new Edge(6, 2, 1.0),new Edge(2, 6, 1.0),new Edge(2, 1, 1.0),new Edge(1, 2, 1.0),new Edge(1, 8, 1.0),new Edge(8, 1, 1.0),new Edge(8, 7, 1.0),new Edge(7, 8, 1.0),new Edge(7, 5, 1.0),new Edge(5, 7, 1.0),new Edge(5, 4, 1.0),new Edge(4, 5, 1.0),new Edge(4, 3, 1.0),new Edge(3, 4, 1.0),new Edge(3, 6, 1.0),new Edge(6, 3, 1.0),new Edge(6, 7, 1.0),new Edge(7, 6, 1.0),new Edge(7, 9, 1.0),new Edge(9, 7, 1.0),new Edge(9, 8, 1.0),new Edge(8, 9, 1.0),new Edge(8, 2, 1.0),new Edge(2, 8, 1.0),new Edge(2, 0, 1.0),new Edge(0, 2, 1.0),new Edge(0, 4, 1.0),new Edge(4, 0, 1.0),new Edge(4, 6, 1.0),new Edge(6, 4, 1.0),new Edge(5, 6, 1.0),new Edge(6, 5, 1.0),new Edge(6, 8, 1.0),new Edge(8, 6, 1.0),new Edge(9, 2, 1.0),new Edge(2, 9, 1.0),new Edge(2, 7, 1.0),new Edge(7, 2, 1.0),new Edge(7, 3, 1.0),new Edge(3, 7, 1.0),new Edge(3, 8, 1.0),new Edge(8, 3, 1.0),new Edge(4, 8, 1.0),new Edge(8, 4, 1.0),new Edge(2, 4, 1.0),new Edge(4, 2, 1.0),new Edge(2, 3, 1.0),new Edge(3, 2, 1.0),new Edge(5, 3, 1.0),new Edge(3, 5, 1.0),new Edge(0, 5, 1.0),new Edge(5, 0, 1.0),new Edge(6, 1, 1.0),new Edge(1, 6, 1.0),new Edge(6, 9, 1.0),new Edge(9, 6, 1.0),new Edge(7, 1, 1.0),new Edge(1, 7, 1.0),new Edge(1, 4, 1.0),new Edge(4, 1, 1.0),new Edge(1, 9, 1.0),new Edge(9, 1, 1.0),new Edge(8, 0, 1.0),new Edge(0, 8, 1.0),new Edge(0, 7, 1.0),new Edge(7, 0, 1.0),new Edge(2, 5, 1.0),new Edge(5, 2, 1.0),new Edge(5, 1, 1.0),new Edge(1, 5, 1.0),new Edge(8, 5, 1.0),new Edge(5, 8, 1.0),new Edge(5, 9, 1.0),new Edge(9, 5, 1.0),new Edge(7, 4, 1.0),new Edge(4, 7, 1.0),new Edge(3, 9, 1.0),new Edge(9, 3, 1.0),new Edge(0, 9, 1.0),new Edge(9, 0, 1.0),new Edge(9, 4, 1.0),new Edge(4, 9, 1.0)), 10);
    private final static Graph weightedGraph = new Graph(List.of(new Edge(0, 1, 10.0), new Edge(1, 0, 10.0), new Edge(1, 4, 1.0), new Edge(4, 1, 1.0), new Edge(4, 3, 1.0), new Edge(3, 4, 1.0), new Edge(3, 5, 3.0), new Edge(5, 3, 3.0), new Edge(5, 2, 1.0), new Edge(2, 5, 1.0), new Edge(2, 0, 1.0), new Edge(0, 2, 1.0), new Edge(0, 4, 1.0), new Edge(4, 0, 1.0), new Edge(4, 2, 1.0), new Edge(2, 4, 1.0), new Edge(2, 3, 2.0), new Edge(3, 2, 2.0)), 6);

    private static Stream<Arguments> input() {
        return Stream.of(
                Arguments.of(emptyGraph, emptyList()),
                Arguments.of(oneNodeGraph, emptyList()),
                Arguments.of(twoNodesGraph, List.of(new Edge(0, 1, 1.0))),
                Arguments.of(triangleGraph, List.of(new Edge(0, 1, 1.0), new Edge(1, 2, 1.0))),
                Arguments.of(completeGraph, List.of(new Edge(0, 1, 1.0), new Edge(1, 3, 1.0), new Edge(0, 6, 1.0), new Edge(6, 2, 1.0), new Edge(1, 8, 1.0), new Edge(8, 7, 1.0), new Edge(7, 5, 1.0), new Edge(5, 4, 1.0), new Edge(7, 9, 1.0))),
                Arguments.of(weightedGraph, List.of(new Edge(1, 4, 1.0), new Edge(4, 3, 1.0), new Edge(5, 2, 1.0), new Edge(2, 0, 1.0), new Edge(0, 4, 1.0)))
        );
    }
}


