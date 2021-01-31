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

public class FindStronglyConnectedComponentsTest {

    @ParameterizedTest
    @MethodSource("input")
    public void taskTest(Graph graph, List<Set<Integer>> expected) {
        //given
        Task task = new FindStronglyConnectedComponents();

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
    private final static Graph twoComponentsGraph = new Graph(List.of(new Edge(0, 1, 1.0),new Edge(1, 2, 1.0),new Edge(2, 0, 1.0),new Edge(0, 3, 1.0),new Edge(3, 3, 1.0),new Edge(3, 4, 1.0),new Edge(4, 3, 1.0),new Edge(4, 5, 1.0),new Edge(5, 6, 1.0),new Edge(6, 4, 1.0)), 7);
    private final static Graph threeComponents = new Graph(List.of(new Edge(0, 1, 1.0), new Edge(1, 2, 1.0), new Edge(2, 3, 1.0), new Edge(3, 2, 1.0), new Edge(3, 4, 1.0), new Edge(4, 5, 1.0), new Edge(5, 4, 1.0), new Edge(1, 6, 1.0), new Edge(6, 0, 1.0)), 7);
    private final static Graph fourComponentsGraph = new Graph(List.of(new Edge(0, 1, 1.0), new Edge(1, 0, 1.0), new Edge(0, 8, 1.0), new Edge(8, 0, 1.0), new Edge(8, 7, 1.0), new Edge(7, 6, 1.0), new Edge(6, 7, 1.0), new Edge(1, 7, 1.0), new Edge(2, 1, 1.0), new Edge(2, 6, 1.0), new Edge(5, 6, 1.0), new Edge(2, 5, 1.0), new Edge(5, 3, 1.0), new Edge(3, 2, 1.0), new Edge(4, 3, 1.0), new Edge(4, 5, 1.0)), 9);
    private final static Graph twoDisjointComponentsGraph = new Graph(List.of(new Edge(0, 1, 1.0), new Edge(1, 0, 1.0), new Edge(2, 3, 1.0), new Edge(3, 4, 1.0), new Edge(4, 2, 1.0)), 5);
    private final static Graph butterflyGraph = new Graph(List.of(new Edge(0, 1, 1.0), new Edge(1, 2, 1.0), new Edge(2, 3, 1.0), new Edge(3, 1, 1.0), new Edge(1, 4, 1.0), new Edge(4, 0, 1.0)), 5);

    private static Stream<Arguments> input() {
        return Stream.of(
                Arguments.of(emptyGraph, emptyList()),
                Arguments.of(oneNodeGraph, List.of(Set.of(0))),
                Arguments.of(twoNodesGraph, List.of(Set.of(0), Set.of(1))),
                Arguments.of(triangleGraph, List.of(Set.of(0, 1, 2))),
                Arguments.of(twoComponentsGraph, List.of(Set.of(0, 1, 2), Set.of(3, 4, 5, 6))),
                Arguments.of(threeComponents, List.of(Set.of(0, 1, 6), Set.of(2, 3), Set.of(4, 5))),
                Arguments.of(twoDisjointComponentsGraph, List.of(Set.of(0, 1), Set.of(2, 3, 4))),
                Arguments.of(fourComponentsGraph, List.of(Set.of(0, 1, 8), Set.of(6, 7), Set.of(2, 3, 5), Set.of(4))),
                Arguments.of(butterflyGraph, List.of(Set.of(0, 1, 2, 3, 4)))
        );
    }

    @Test
    public void executeTask_success() {
        //given
        Map<String, Integer> params = emptyMap();
        Graph graph = emptyGraph;
        Config config = new Config(true, true, true);

        //when
        TaskResult result = TaskDefinition.FIND_STRONGLY_CONNECTED_COMPONENTS.execute(graph, config, params);

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
        TaskResult result = TaskDefinition.FIND_STRONGLY_CONNECTED_COMPONENTS.execute(graph, config, params);

        //then
        assertThat(result, isA(TaskExecutionFailed.class));
        assertTrue(result.failed());
        assertEquals("Wrong parameters number", ((TaskExecutionFailed)result).getMessage());
    }

    @Test
    public void validatePrerequisites_failOnUndisconnectedGraph() {
        //given
        Map<String, Integer> params = emptyMap();
        Graph graph = new Graph(List.of(new Edge(1, 2, 1.0),new Edge(2, 1, 1.0)), 2);
        Config config = new Config(false, true, true);

        //when
        TaskResult result = TaskDefinition.FIND_STRONGLY_CONNECTED_COMPONENTS.execute(graph, config, params);

        //then
        assertThat(result, isA(TaskExecutionFailed.class));
        assertTrue(result.failed());
        assertEquals("Graph must be directional", ((TaskExecutionFailed)result).getMessage());
    }
}


