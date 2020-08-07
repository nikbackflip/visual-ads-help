package com.backflip.vadsh.template.graph;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.backflip.vadsh.template.graph.GraphArgs.builder;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyArray;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmptyGraphTest extends AbstractGraphTest {

    @BeforeAll
    static void init() throws IOException {
        GraphArgs args = builder().build();

        init(args);
    }

    @Test
    public void adjacencyMatrixView() {
        //given

        //when
        double[][] graph = graphReflect.adjacencyMatrix();

        //then
        assertThat(graph, emptyArray());
    }

    @Test
    public void edgeList() {
        //given

        //when
        List<?> graph = graphReflect.edgeList();

        //then
        assertEquals(0, graph.size());
    }

    @Test
    public void edgeArray() {
        //given

        //when
        Object[] graph = graphReflect.edgeArray();

        //then
        assertEquals(0, graph.length);
    }

    @Test
    public void adjacencyList() {
        //given

        //when
        List<List<Integer>> graph = graphReflect.adjacencyList();

        //then
        assertEquals(0, graph.size());
    }

    @Test
    public void adjacencyListAsMap() {
        //given

        //when
        Map<Integer, List<Object>> graph = graphReflect.adjacencyListAsMap();

        //then
        assertEquals(0, graph.size());
    }

    @Test
    public void adjacencyListOfEdges() {
        //given

        //when
        List<List<Object>> graph = graphReflect.adjacencyListOfEdges();

        //then
        assertEquals(0, graph.size());
    }

}
