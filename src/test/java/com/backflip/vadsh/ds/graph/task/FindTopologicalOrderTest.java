package com.backflip.vadsh.ds.graph.task;

import com.backflip.vadsh.ds.graph.Config;
import com.backflip.vadsh.ds.graph.Edge;
import com.backflip.vadsh.ds.graph.Graph;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isA;
import static org.junit.jupiter.api.Assertions.*;

public class FindTopologicalOrderTest {

    @ParameterizedTest
    @MethodSource("input")
    public void taskTest(Graph graph, List<Integer> expected) {
        //given
        Task task = new FindTopologicalOrder();

        //when
        TaskResult result = task.execute(graph, config, emptyMap());

        //then
        assertEquals(expected, ((TaskExecutionSuccess)result).getNodes());
    }

    private final static Config config = new Config(true, true, false);
    private final static Graph oneNodeGraph = new Graph(emptyList(), 1);
    private final static Graph twoNodesGraph = new Graph(List.of(new Edge(0, 1, 1.0)), 2);
    private final static Graph dag = new Graph(List.of(new Edge(0, 1, 1.0), new Edge(1, 3, 1.0), new Edge(3, 7, 1.0), new Edge(7, 9, 1.0), new Edge(0, 4, 1.0), new Edge(4, 6, 1.0), new Edge(6, 8, 1.0), new Edge(0, 2, 1.0), new Edge(2, 7, 1.0), new Edge(4, 5, 1.0)), 10);
    private final static Graph disconnectedDag = new Graph(List.of(new Edge(3, 0, 1.0), new Edge(0, 4, 1.0), new Edge(4, 1, 1.0), new Edge(2, 6, 1.0), new Edge(6, 5, 1.0), new Edge(5, 9, 1.0), new Edge(9, 1, 1.0), new Edge(3, 1, 1.0), new Edge(1, 12, 1.0), new Edge(12, 11, 1.0), new Edge(10, 8, 1.0), new Edge(8, 7, 1.0), new Edge(7, 13, 1.0), new Edge(13, 14, 1.0), new Edge(8, 13, 1.0), new Edge(6, 0, 1.0), new Edge(9, 0, 1.0)), 15);

    private static Stream<Arguments> input() {
        return Stream.of(
                Arguments.of(oneNodeGraph, List.of(0)),
                Arguments.of(twoNodesGraph, List.of(0, 1)),
                Arguments.of(dag, List.of(0, 2, 4, 5, 6, 8, 1, 3, 7, 9)),
                Arguments.of(disconnectedDag, List.of(10, 8, 7, 13, 14, 3, 2, 6, 5, 9, 0, 4, 1, 12, 11))
        );
    }

    @Test
    public void executeTask_success() {
        //given
        Map<String, Integer> params = emptyMap();
        Graph graph = twoNodesGraph;
        Config config = new Config(true, true, false);

        //when
        TaskResult result = TaskDefinition.FIND_TOPOLOGICAL_ORDER.execute(graph, config, params);

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
        TaskResult result = TaskDefinition.FIND_TOPOLOGICAL_ORDER.execute(graph, config, params);

        //then
        assertThat(result, isA(TaskExecutionFailed.class));
        assertTrue(result.failed());
        assertEquals("Wrong parameters number", ((TaskExecutionFailed)result).getMessage());
    }

    @Test
    public void validatePrerequisites_failOnNotDag() {
        //given
        Map<String, Integer> params = emptyMap();
        Graph graph = new Graph(List.of(new Edge(0, 0, -1)), 1);
        Config config = new Config(true, true, true);

        //when
        TaskResult result = TaskDefinition.FIND_TOPOLOGICAL_ORDER.execute(graph, config, params);

        //then
        assertThat(result, isA(TaskExecutionFailed.class));
        assertTrue(result.failed());
        assertEquals("Graph must be a directed acyclic graph", ((TaskExecutionFailed)result).getMessage());
    }
}