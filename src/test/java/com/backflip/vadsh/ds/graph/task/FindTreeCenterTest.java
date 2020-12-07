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
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class FindTreeCenterTest {

    @ParameterizedTest
    @MethodSource("input")
    public void taskTest(Graph graph, List<Integer> expected) {
        //given
        Task task = new FindTreeCenter();

        //when
        TaskResult result = task.execute(graph, notDirectionalConfig, emptyMap());

        //then
        assertEquals(expected, ((TaskExecutionSuccess)result).getNodes());
    }

    private final static Config notDirectionalConfig = new Config(false, true, true);
    private final static Graph emptyTree = new Graph(List.of(), 0);
    private final static Graph oneNodeTree = new Graph(List.of(), 1);
    private final static Graph twoNodesTree = new Graph(List.of(new Edge(0, 1, 1.0), new Edge(1, 0, 1.0)), 2);
    private final static Graph lineTree = new Graph(List.of(new Edge(0, 1, 1.0),new Edge(1, 0, 1.0),new Edge(1, 2, 1.0),new Edge(2, 1, 1.0),new Edge(4, 3, 1.0),new Edge(3, 4, 1.0),new Edge(3, 2, 1.0),new Edge(2, 3, 1.0)), 5);
    private final static Graph bigTree = new Graph(List.of(new Edge(0, 2, 1.0),new Edge(2, 0, 1.0),new Edge(2, 6, 1.0),new Edge(6, 2, 1.0),new Edge(6, 11, 1.0),new Edge(11, 6, 1.0),new Edge(11, 12, 1.0),new Edge(12, 11, 1.0),new Edge(12, 7, 1.0),new Edge(7, 12, 1.0),new Edge(5, 2, 1.0),new Edge(2, 5, 1.0),new Edge(2, 10, 1.0),new Edge(10, 2, 1.0),new Edge(1, 4, 1.0),new Edge(4, 1, 1.0),new Edge(4, 3, 1.0),new Edge(3, 4, 1.0),new Edge(3, 9, 1.0),new Edge(9, 3, 1.0),new Edge(8, 13, 1.0),new Edge(13, 8, 1.0),new Edge(8, 6, 1.0),new Edge(6, 8, 1.0),new Edge(3, 11, 1.0),new Edge(11, 3, 1.0)), 14);
    private final static Graph bigTree2 = new Graph(List.of(new Edge(0, 1, 1.0), new Edge(1, 0, 1.0), new Edge(1, 2, 1.0), new Edge(2, 1, 1.0), new Edge(2, 3, 1.0), new Edge(3, 2, 1.0), new Edge(13, 1, 1.0), new Edge(1, 13, 1.0), new Edge(1, 11, 1.0), new Edge(11, 1, 1.0), new Edge(12, 11, 1.0), new Edge(11, 12, 1.0), new Edge(12, 5, 1.0), new Edge(5, 12, 1.0), new Edge(2, 9, 1.0), new Edge(9, 2, 1.0), new Edge(4, 6, 1.0), new Edge(6, 4, 1.0), new Edge(6, 1, 1.0), new Edge(1, 6, 1.0), new Edge(1, 8, 1.0), new Edge(8, 1, 1.0), new Edge(14, 10, 1.0), new Edge(10, 14, 1.0), new Edge(10, 0, 1.0), new Edge(0, 10, 1.0), new Edge(5, 7, 1.0), new Edge(7, 5, 1.0)), 15);

    private static Stream<Arguments> input() {
        return Stream.of(
                Arguments.of(emptyTree, emptyList()),
                Arguments.of(oneNodeTree, List.of(0)),
                Arguments.of(twoNodesTree, List.of(0, 1)),
                Arguments.of(lineTree, List.of(2)),
                Arguments.of(bigTree, List.of(11)),
                Arguments.of(bigTree2, List.of(1, 11))
        );
    }

    @Test
    public void executeTask_success() {
        //given
        Map<String, Integer> params = emptyMap();
        Graph graph = bigTree;
        Config config = new Config(false, true, true);

        //when
        TaskResult result = TaskDefinition.FIND_TREE_CERNER.execute(graph, config, params);

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
        TaskResult result = TaskDefinition.FIND_TREE_CERNER.execute(graph, config, params);

        //then
        assertThat(result, isA(TaskExecutionFailed.class));
        assertTrue(result.failed());
        assertEquals("Wrong parameters number", ((TaskExecutionFailed)result).getMessage());
    }

    @Test
    public void validatePrerequisites_failOnDirectionalGraph() {
        //given
        Map<String, Integer> params = emptyMap();
        Graph graph = new Graph(emptyList(), 0);
        Config config = new Config(true, true, true);

        //when
        TaskResult result = TaskDefinition.FIND_TREE_CERNER.execute(graph, config, params);

        //then
        assertThat(result, isA(TaskExecutionFailed.class));
        assertTrue(result.failed());
        assertEquals("Graph must be undirected", ((TaskExecutionFailed)result).getMessage());
    }

    @Test
    public void validatePrerequisites_failOnGraphNotATree() {
        //given
        Map<String, Integer> params = emptyMap();
        Graph graph = new Graph(List.of(new Edge(0, 1, 1.0), new Edge(1, 0, 1.0), new Edge(1, 2, 1.0), new Edge(2, 1, 1.0), new Edge(2, 0, 1.0), new Edge(0, 2, 1.0)), 3);
        Config config = new Config(false, true, true);

        //when
        TaskResult result = TaskDefinition.FIND_TREE_CERNER.execute(graph, config, params);

        //then
        assertThat(result, isA(TaskExecutionFailed.class));
        assertTrue(result.failed());
        assertEquals("Graph must be a tree", ((TaskExecutionFailed)result).getMessage());
    }
}
