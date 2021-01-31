package com.backflip.vadsh.ds.graph.task;

import com.backflip.vadsh.ds.graph.Config;
import com.backflip.vadsh.ds.graph.Edge;
import com.backflip.vadsh.ds.graph.Graph;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.isA;
import static org.junit.jupiter.api.Assertions.*;

public class FindBridgesTest {

    @ParameterizedTest
    @MethodSource("input")
    public void taskTest(Graph graph, List<Edge> expected) {
        //given
        Task task = new FindBridges();

        //when
        TaskResult result = task.execute(graph, undirectionalConfig, Collections.emptyMap());

        //then
        assertThat(((TaskExecutionSuccess)result).getEdges(), containsInAnyOrder(expected.toArray()));
    }

    private final static Config undirectionalConfig = new Config(false, true, true);
    private final static Graph emptyGraph = new Graph(List.of(), 0);
    private final static Graph oneNodeGraph = new Graph(List.of(), 1);
    private final static Graph twoNodesGraph = new Graph(List.of(new Edge(0, 1, 1.0), new Edge(1, 0, 1.0)), 2);
    private final static Graph triangleGraph = new Graph(List.of(new Edge(0, 1, 1.0), new Edge(1, 0, 1.0), new Edge(1, 2, 1.0), new Edge(2, 1, 1.0), new Edge(2, 0, 1.0), new Edge(0, 2, 1.0)), 3);
    private final static Graph twoComponentsGraph = new Graph(List.of(new Edge(0, 1, 1.0), new Edge(0, 2, 1.0), new Edge(1, 0, 1.0), new Edge(1, 2, 1.0), new Edge(2, 0, 1.0), new Edge(2, 1, 1.0), new Edge(2, 3, 1.0), new Edge(3, 2, 1.0), new Edge(3, 4, 1.0), new Edge(3, 5, 1.0), new Edge(4, 3, 1.0), new Edge(4, 6, 1.0), new Edge(5, 3, 1.0), new Edge(5, 6, 1.0), new Edge(6, 4, 1.0), new Edge(6, 5, 1.0)), 7);
    private final static Graph threeComponentsGraph = new Graph(List.of(new Edge(0, 1, 1.0), new Edge(1, 0, 1.0), new Edge(1, 2, 1.0), new Edge(2, 1, 1.0), new Edge(2, 0, 1.0), new Edge(0, 2, 1.0), new Edge(2, 3, 1.0), new Edge(3, 2, 1.0), new Edge(3, 4, 1.0), new Edge(4, 3, 1.0), new Edge(4, 5, 1.0), new Edge(5, 4, 1.0), new Edge(5, 6, 1.0), new Edge(6, 5, 1.0), new Edge(6, 3, 1.0), new Edge(3, 6, 1.0), new Edge(2, 7, 1.0), new Edge(7, 2, 1.0), new Edge(7, 8, 1.0), new Edge(8, 7, 1.0), new Edge(8, 9, 1.0), new Edge(9, 8, 1.0), new Edge(9, 7, 1.0), new Edge(7, 9, 1.0)), 10);
    private final static Graph fourComponentsGraph = new Graph(List.of(new Edge(0, 1, 1.0), new Edge(1, 0, 1.0), new Edge(1, 2, 1.0), new Edge(2, 1, 1.0), new Edge(2, 0, 1.0), new Edge(0, 2, 1.0), new Edge(2, 3, 1.0), new Edge(3, 2, 1.0), new Edge(3, 4, 1.0), new Edge(4, 3, 1.0), new Edge(4, 5, 1.0), new Edge(5, 4, 1.0), new Edge(5, 6, 1.0), new Edge(6, 5, 1.0), new Edge(6, 3, 1.0), new Edge(3, 6, 1.0), new Edge(2, 7, 1.0), new Edge(7, 2, 1.0), new Edge(7, 8, 1.0), new Edge(8, 7, 1.0), new Edge(8, 9, 1.0), new Edge(9, 8, 1.0), new Edge(9, 7, 1.0), new Edge(7, 9, 1.0), new Edge(5, 10, 1.0), new Edge(10, 5, 1.0)), 11);
    private final static Graph tree = new Graph(List.of(new Edge(0, 2, 1.0),new Edge(2, 0, 1.0),new Edge(2, 6, 1.0),new Edge(6, 2, 1.0),new Edge(6, 11, 1.0),new Edge(11, 6, 1.0),new Edge(11, 12, 1.0),new Edge(12, 11, 1.0),new Edge(12, 7, 1.0),new Edge(7, 12, 1.0),new Edge(5, 2, 1.0),new Edge(2, 5, 1.0),new Edge(2, 10, 1.0),new Edge(10, 2, 1.0),new Edge(1, 4, 1.0),new Edge(4, 1, 1.0),new Edge(4, 3, 1.0),new Edge(3, 4, 1.0),new Edge(3, 9, 1.0),new Edge(9, 3, 1.0),new Edge(8, 13, 1.0),new Edge(13, 8, 1.0),new Edge(8, 6, 1.0),new Edge(6, 8, 1.0),new Edge(3, 11, 1.0),new Edge(11, 3, 1.0)), 14);
    private final static Graph disconnectedComponents = new Graph(List.of(new Edge(0, 1, 1.0), new Edge(1, 0, 1.0), new Edge(1, 2, 1.0), new Edge(2, 1, 1.0), new Edge(2, 0, 1.0), new Edge(0, 2, 1.0), new Edge(3, 4, 1.0), new Edge(4, 3, 1.0), new Edge(4, 5, 1.0), new Edge(5, 4, 1.0), new Edge(5, 3, 1.0), new Edge(3, 5, 1.0)), 6);
    private final static Graph butterflyGraph = new Graph(List.of(new Edge(0, 1, 1.0), new Edge(1, 0, 1.0), new Edge(1, 2, 1.0), new Edge(2, 1, 1.0), new Edge(2, 3, 1.0), new Edge(3, 2, 1.0), new Edge(3, 4, 1.0), new Edge(4, 3, 1.0), new Edge(4, 2, 1.0), new Edge(2, 4, 1.0), new Edge(2, 0, 1.0), new Edge(0, 2, 1.0)), 5);

    private static Stream<Arguments> input() {
        return Stream.of(
                Arguments.of(emptyGraph, emptyList()),
                Arguments.of(oneNodeGraph, emptyList()),
                Arguments.of(twoNodesGraph, List.of(new Edge(0, 1, 1.0))),
                Arguments.of(triangleGraph, emptyList()),
                Arguments.of(twoComponentsGraph, List.of(new Edge(2, 3, 1.0))),
                Arguments.of(threeComponentsGraph, List.of(new Edge(2, 3, 1.0), new Edge(2, 7, 1.0))),
                Arguments.of(fourComponentsGraph, List.of(new Edge(2, 3, 1.0), new Edge(2, 7, 1.0), new Edge(5, 10, 1.0))),
                Arguments.of(tree, List.of(new Edge(0, 2, 1.0), new Edge(2, 6, 1.0), new Edge(6, 11, 1.0), new Edge(11, 12, 1.0), new Edge(12, 7, 1.0) ,new Edge(2, 5, 1.0), new Edge(2, 10, 1.0), new Edge(4, 1, 1.0), new Edge(3, 4, 1.0), new Edge(3, 9, 1.0), new Edge(8, 13, 1.0), new Edge(6, 8, 1.0), new Edge(11, 3, 1.0))),
                Arguments.of(disconnectedComponents, emptyList()),
                Arguments.of(butterflyGraph, emptyList())
        );
    }

    @Test
    public void executeTask_success() {
        //given
        Map<String, Integer> params = emptyMap();
        Graph graph = triangleGraph;
        Config config = new Config(false, true, true);

        //when
        TaskResult result = TaskDefinition.FIND_BRIDGES.execute(graph, config, params);

        //then
        assertThat(result, isA(TaskExecutionSuccess.class));
        assertFalse(result.failed());
    }

    @Test
    public void validateParameters_failOnParametersPresent() {
        //given
        Map<String, Integer> params = Map.of("test", 0);
        Graph graph = new Graph(emptyList(), 0);
        Config config = new Config(true, true, true);

        //when
        TaskResult result = TaskDefinition.FIND_BRIDGES.execute(graph, config, params);

        //then
        assertThat(result, isA(TaskExecutionFailed.class));
        assertTrue(result.failed());
        assertEquals("Wrong parameters number", ((TaskExecutionFailed)result).getMessage());
    }

    @Test
    public void validatePrerequisites_failOnDisconnectedGraph() {
        //given
        Map<String, Integer> params = emptyMap();
        Graph graph = new Graph(List.of(new Edge(1, 2, 1.0)), 2);
        Config config = new Config(true, true, true);

        //when
        TaskResult result = TaskDefinition.FIND_BRIDGES.execute(graph, config, params);

        //then
        assertThat(result, isA(TaskExecutionFailed.class));
        assertTrue(result.failed());
        assertEquals("Graph must be undirected", ((TaskExecutionFailed)result).getMessage());
    }
}
