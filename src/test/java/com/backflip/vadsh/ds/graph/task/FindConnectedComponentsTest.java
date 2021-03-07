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
import java.util.Set;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.isA;
import static org.junit.jupiter.api.Assertions.*;

public class FindConnectedComponentsTest {

    @ParameterizedTest
    @MethodSource("input")
    public void taskTest(Graph graph, List<Set<Integer>> expected) {
        //given
        Task task = new FindConnectedComponents();

        //when
        TaskResult result = task.execute(graph, directionalConfig, Collections.emptyMap());

        //then
        assertThat(((TaskExecutionSuccess)result).getComponents(), containsInAnyOrder(expected.toArray()));
    }

    private final static Config directionalConfig = new Config(true, true, true);
    private final static Graph emptyGraph = new Graph(List.of(), 0);
    private final static Graph oneNodeGraph = new Graph(List.of(), 1);
    private final static Graph twoNodesGraph = new Graph(List.of(), 2);
    private final static Graph triangleGraph = new Graph(List.of(new Edge(0, 1, 1.0), new Edge(1, 0, 1.0), new Edge(1, 2, 1.0), new Edge(2, 1, 1.0), new Edge(2, 0, 1.0), new Edge(0, 2, 1.0)), 3);
    private final static Graph disconnectedGraph = new Graph(List.of(new Edge(1, 2, 1.0), new Edge(2, 1, 1.0), new Edge(2, 4, 1.0), new Edge(4, 2, 1.0), new Edge(4, 1, 1.0), new Edge(1, 4, 1.0), new Edge(0, 3, 1.0), new Edge(3, 0, 1.0)), 5);
    private final static Graph disconnectedUndirectedGraph = new Graph(List.of(new Edge(3, 5, 1.0), new Edge(5, 3, 1.0), new Edge(5, 4, 1.0), new Edge(4, 5, 1.0), new Edge(4, 6, 1.0), new Edge(6, 4, 1.0), new Edge(6, 3, 1.0), new Edge(3, 6, 1.0), new Edge(2, 3, 1.0), new Edge(3, 2, 1.0), new Edge(7, 8, 1.0), new Edge(8, 7, 1.0), new Edge(10, 10, 1.0), new Edge(0, 9, 1.0), new Edge(9, 0, 1.0)), 11);
    private final static Graph disconnectedDirectedGraph = new Graph(List.of(new Edge(1, 0, 1.0), new Edge(2, 3, 1.0), new Edge(3, 9, 1.0), new Edge(9, 2, 1.0), new Edge(5, 9, 1.0), new Edge(4, 4, 1.0), new Edge(7, 7, 1.0), new Edge(8, 8, 1.0), new Edge(8, 7, 1.0), new Edge(6, 2, 1.0)), 10);

    private static Stream<Arguments> input() {
        return Stream.of(
                Arguments.of(emptyGraph, emptyList()),
                Arguments.of(oneNodeGraph, List.of(Set.of(0))),
                Arguments.of(twoNodesGraph, List.of(Set.of(0), Set.of(1))),
                Arguments.of(triangleGraph, List.of(Set.of(0, 1, 2))),
                Arguments.of(disconnectedGraph, List.of(Set.of(0, 3), Set.of(1, 2, 4))),
                Arguments.of(disconnectedUndirectedGraph, List.of(Set.of(0, 9), Set.of(1), Set.of(2, 3, 4, 5, 6), Set.of(7, 8), Set.of(10))),
                Arguments.of(disconnectedDirectedGraph, List.of(Set.of(0, 1), Set.of(4), Set.of(2, 3, 5, 6, 9), Set.of(7, 8)))
        );
    }

    @Test
    public void executeTask_success() {
        //given
        Map<String, Integer> params = emptyMap();
        Graph graph = emptyGraph;
        Config config = new Config(true, true, true);

        //when
        TaskResult result = TaskDefinition.FIND_CONNECTED_COMPONENTS.execute(graph, config, params);

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
        TaskResult result = TaskDefinition.FIND_CONNECTED_COMPONENTS.execute(graph, config, params);

        //then
        assertThat(result, isA(TaskExecutionFailed.class));
        assertTrue(result.failed());
        assertEquals("Wrong parameters number", ((TaskExecutionFailed)result).getMessage());
    }
}


