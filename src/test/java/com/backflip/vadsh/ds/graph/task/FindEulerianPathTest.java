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

public class FindEulerianPathTest {

    @ParameterizedTest
    @MethodSource("input")
    public void taskTest(Graph graph, List<Integer> expected) {
        //given
        Task task = new FindEulerianPath();

        //when
        TaskResult result = task.execute(graph, directionalConfig, emptyMap());

        //then
        assertEquals(expected, ((TaskExecutionSuccess)result).getNodes());
    }

    private final static Config directionalConfig = new Config(true, true, true);
    private final static Graph emptyGraph = new Graph(List.of(), 0);
    private final static Graph oneNodeGraph = new Graph(List.of(), 1);
    private final static Graph disconnectedNodesGraph = new Graph(List.of(), 3);
    private final static Graph singleSelfLoopGraph = new Graph(List.of(new Edge(0, 0, 1.0)), 1);
    private final static Graph twoNodesGraph = new Graph(List.of(new Edge(0, 1, 1.0)), 2);
    private final static Graph lineGraph = new Graph(List.of(new Edge(0, 1, 1.0), new Edge(1, 2, 1.0), new Edge(2, 3, 1.0), new Edge(3, 4, 1.0)), 5);
    private final static Graph triangleGraph = new Graph(List.of(new Edge(0, 1, 1.0), new Edge(1, 2, 1.0), new Edge(2, 0, 1.0)), 3);
    private final static Graph bigGraph = new Graph(List.of(new Edge(0, 1, 1.0), new Edge(1, 2, 1.0), new Edge(1, 3, 1.0), new Edge(3, 1, 1.0), new Edge(2, 4, 1.0), new Edge(4, 3, 1.0), new Edge(3, 5, 1.0), new Edge(5, 5, 1.0), new Edge(5, 0, 1.0)), 6);
    private final static Graph bigGraph2 = new Graph(List.of(new Edge(0, 2, 1.0), new Edge(1, 3, 1.0), new Edge(2, 1, 1.0), new Edge(3, 0, 1.0), new Edge(3, 4, 1.0), new Edge(6, 3, 1.0), new Edge(6, 7, 1.0), new Edge(7, 8, 1.0), new Edge(8, 9, 1.0), new Edge(9, 6, 1.0)), 10);
    private final static Graph fullGraph = new Graph(List.of(new Edge(0, 1, 1.0), new Edge(1, 0, 1.0), new Edge(0, 2, 1.0), new Edge(2, 0, 1.0), new Edge(0, 3, 1.0), new Edge(3, 0, 1.0), new Edge(0, 4, 1.0), new Edge(4, 0, 1.0), new Edge(1, 2, 1.0), new Edge(2, 1, 1.0), new Edge(1, 3, 1.0), new Edge(3, 1, 1.0), new Edge(1, 4, 1.0), new Edge(4, 1, 1.0), new Edge(2, 3, 1.0), new Edge(3, 2, 1.0), new Edge(2, 4, 1.0), new Edge(4, 2, 1.0), new Edge(3, 4, 1.0), new Edge(4, 3, 1.0)), 5);

    private static Stream<Arguments> input() {
        return Stream.of(
                Arguments.of(emptyGraph, emptyList()),
                Arguments.of(oneNodeGraph, emptyList()),
                Arguments.of(disconnectedNodesGraph, emptyList()),
                Arguments.of(singleSelfLoopGraph, List.of(0, 0)),
                Arguments.of(twoNodesGraph, List.of(0, 1)),
                Arguments.of(lineGraph, List.of(0, 1, 2, 3, 4)),
                Arguments.of(triangleGraph, List.of(0, 1, 2, 0)),
                Arguments.of(bigGraph, List.of(0, 1, 2, 4, 3, 1, 3, 5, 5, 0)),
                Arguments.of(bigGraph2, List.of(6, 7, 8, 9, 6, 3, 0, 2, 1, 3, 4)),
                Arguments.of(fullGraph, List.of(0, 1, 0, 2, 0, 3, 0, 4, 1, 2, 1, 3, 1, 4, 2, 3, 2, 4, 3, 4, 0))
        );
    }

    @Test
    public void executeTask_success() {
        //given
        Map<String, Integer> params = emptyMap();
        Graph graph = bigGraph;
        Config config = new Config(true, true, true);

        //when
        TaskResult result = TaskDefinition.FIND_EULERIAN_PATH.execute(graph, config, params);

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
        TaskResult result = TaskDefinition.FIND_EULERIAN_PATH.execute(graph, config, params);

        //then
        assertThat(result, isA(TaskExecutionFailed.class));
        assertTrue(result.failed());
        assertEquals("Wrong parameters number", ((TaskExecutionFailed)result).getMessage());
    }

    @Test
    public void validatePrerequisites_failOnUndirectionalGraph() {
        //given
        Map<String, Integer> params = emptyMap();
        Graph graph = new Graph(emptyList(), 0);
        Config config = new Config(false, true, true);

        //when
        TaskResult result = TaskDefinition.FIND_EULERIAN_PATH.execute(graph, config, params);

        //then
        assertThat(result, isA(TaskExecutionFailed.class));
        assertTrue(result.failed());
        assertEquals("Graph must be directional", ((TaskExecutionFailed)result).getMessage());
    }

    @Test
    public void validatePrerequisites_failOnGraphDoesNotHaveEulerianPath() {
        //given
        Map<String, Integer> params = emptyMap();
        Graph graph = new Graph(List.of(new Edge(0, 1, 1.0), new Edge(0, 2, 1.0)), 3);
        Config config = new Config(true, true, true);

        //when
        TaskResult result = TaskDefinition.FIND_EULERIAN_PATH.execute(graph, config, params);

        //then
        assertThat(result, isA(TaskExecutionFailed.class));
        assertTrue(result.failed());
        assertEquals("Eulerian path does not exist", ((TaskExecutionFailed)result).getMessage());
    }

    @Test
    public void validatePrerequisites_failOnDisconnectedComponent() {
        //given
        Map<String, Integer> params = emptyMap();
        Graph graph = new Graph(List.of(new Edge(0, 1, 1.0), new Edge(0, 2, 1.0), new Edge(3, 4, 1.0)), 5);
        Config config = new Config(true, true, true);

        //when
        TaskResult result = TaskDefinition.FIND_EULERIAN_PATH.execute(graph, config, params);

        //then
        assertThat(result, isA(TaskExecutionFailed.class));
        assertTrue(result.failed());
        assertEquals("Eulerian path does not exist", ((TaskExecutionFailed)result).getMessage());
    }
}
