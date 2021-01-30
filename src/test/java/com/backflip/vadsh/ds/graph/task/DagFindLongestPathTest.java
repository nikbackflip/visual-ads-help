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
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isA;
import static org.junit.jupiter.api.Assertions.*;

public class DagFindLongestPathTest {

    @ParameterizedTest
    @MethodSource("input")
    public void taskTest(Graph graph, Map<String, Integer> params, List<Edge> expected) {
        //given
        Task task = new DagFindLongestPath();

        //when
        TaskResult result = task.execute(graph, config, params);

        //then
        assertEquals(expected, ((TaskExecutionSuccess)result).getEdges());
    }

    private final static Config config = new Config(true, true, false);
    private final static Graph twoNodesGraph = new Graph(List.of(new Edge(0, 1, 1.0)), 2);
    private final static Graph dag = new Graph(List.of(new Edge(0, 1, 1.0), new Edge(1, 3, 1.0), new Edge(3, 8, 1.0), new Edge(8, 10, 1.0), new Edge(0, 4, 1.0), new Edge(4, 7, 1.0), new Edge(7, 9, 1.0), new Edge(0, 2, 1.0), new Edge(2, 8, 1.0), new Edge(4, 5, 1.0)), 11);
    private final static Graph weightedDag = new Graph(List.of(new Edge(0, 1, 1.0), new Edge(1, 4, 1.0), new Edge(4, 3, 11.0), new Edge(0, 2, -1.0), new Edge(2, 5, -1.0), new Edge(5, 3, 1.0), new Edge(1, 3, 1.0)), 6);

    private static Stream<Arguments> input() {
        return Stream.of(
                Arguments.of(twoNodesGraph, Map.of("from", 0, "to", 1), List.of(new Edge(0, 1, 1.0))),
                Arguments.of(dag, Map.of("from", 0, "to", 10), List.of(new Edge(0, 1, 1.0), new Edge(1, 3, 1.0), new Edge(3, 8, 1.0), new Edge(8, 10, 1.0))),
                Arguments.of(weightedDag, Map.of("from", 1, "to", 3), List.of(new Edge(1, 4, 1.0), new Edge(4, 3, 1.0))),
                Arguments.of(weightedDag, Map.of("from", 0, "to", 3), List.of(new Edge(0, 1, 1.0), new Edge(1, 4, 1.0), new Edge(4, 3, 1.0)))
        );
    }

    @Test
    public void executeTask_success() {
        //given
        Map<String, Integer> params = Map.of("from", 0, "to", 1);
        Graph graph = twoNodesGraph;
        Config config = new Config(true, true, false);

        //when
        TaskResult result = TaskDefinition.FIND_LONGEST_PATH_DAG.execute(graph, config, params);

        //then
        assertThat(result, isA(TaskExecutionSuccess.class));
        assertFalse(result.failed());
    }

    @Test
    public void validateParameters_failOnParametersWrongNumber() {
        //given
        Map<String, Integer> params = Map.of("test", 0);
        Graph graph = new Graph(emptyList(), 0);
        Config config = new Config(true, true, true);

        //when
        TaskResult result = TaskDefinition.FIND_LONGEST_PATH_DAG.execute(graph, config, params);

        //then
        assertThat(result, isA(TaskExecutionFailed.class));
        assertTrue(result.failed());
        assertEquals("Wrong parameters number", ((TaskExecutionFailed)result).getMessage());
    }

    @Test
    public void validateParameters_failOnFromNodeAbsent() {
        //given
        Map<String, Integer> params = Map.of("test", 0, "to", 0);
        Graph graph = new Graph(emptyList(), 0);
        Config config = new Config(true, true, true);

        //when
        TaskResult result = TaskDefinition.FIND_LONGEST_PATH_DAG.execute(graph, config, params);

        //then
        assertThat(result, isA(TaskExecutionFailed.class));
        assertTrue(result.failed());
        assertEquals("Parameter From Node absent", ((TaskExecutionFailed)result).getMessage());
    }

    @Test
    public void validateParameters_failOnToNodeAbsent() {
        //given
        Map<String, Integer> params = Map.of("from", 0, "test", 0);
        Graph graph = new Graph(List.of(new Edge(0, 1, 1.0), new Edge(1, 0, 1.0)), 2);
        Config config = new Config(true, true, true);

        //when
        TaskResult result = TaskDefinition.FIND_LONGEST_PATH_DAG.execute(graph, config, params);

        //then
        assertThat(result, isA(TaskExecutionFailed.class));
        assertTrue(result.failed());
        assertEquals("Parameter To Node absent", ((TaskExecutionFailed)result).getMessage());
    }

    @Test
    public void validateParameters_failOnFromNodeOutOfBounds() {
        //given
        Map<String, Integer> params = Map.of("from", -1, "to", 0);
        Graph graph = new Graph(emptyList(), 0);
        Config config = new Config(true, true, true);

        //when
        TaskResult result = TaskDefinition.FIND_LONGEST_PATH_DAG.execute(graph, config, params);

        //then
        assertThat(result, isA(TaskExecutionFailed.class));
        assertTrue(result.failed());
        assertEquals("Parameter From Node is invalid", ((TaskExecutionFailed)result).getMessage());
    }

    @Test
    public void validateParameters_failOnToNodeOutOfBounds() {
        //given
        Map<String, Integer> params = Map.of("from", 0, "to", 99);
        Graph graph = new Graph(emptyList(), 0);
        Config config = new Config(true, true, true);

        //when
        TaskResult result = TaskDefinition.FIND_LONGEST_PATH_DAG.execute(graph, config, params);

        //then
        assertThat(result, isA(TaskExecutionFailed.class));
        assertTrue(result.failed());
        assertEquals("Parameter From Node is invalid", ((TaskExecutionFailed)result).getMessage());
    }

    @Test
    public void validatePrerequisites_failOnNotDag() {
        //given
        Map<String, Integer> params = Map.of("from", 0, "to", 0);
        Graph graph = new Graph(List.of(new Edge(0, 0, -1)), 1);
        Config config = new Config(true, true, true);

        //when
        TaskResult result = TaskDefinition.FIND_LONGEST_PATH_DAG.execute(graph, config, params);

        //then
        assertThat(result, isA(TaskExecutionFailed.class));
        assertTrue(result.failed());
        assertEquals("Graph must be a directed acyclic graph", ((TaskExecutionFailed)result).getMessage());
    }

    @Test
    public void validatePrerequisites_failOnUnreachableNode() {
        //given
        Map<String, Integer> params = Map.of("from", 4, "to", 1);
        Graph graph = weightedDag;
        Config config = new Config(true, true, true);

        //when
        TaskResult result = TaskDefinition.FIND_LONGEST_PATH_DAG.execute(graph, config, params);

        //then
        assertThat(result, isA(TaskExecutionFailed.class));
        assertTrue(result.failed());
        assertEquals("Node 1 is unreachable from node 4", ((TaskExecutionFailed)result).getMessage());
    }
}