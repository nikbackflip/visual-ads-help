package com.backflip.vadsh.template.graph;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.backflip.vadsh.template.graph.GraphArgs.builder;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class NoEdgesGraphTest extends AbstractGraphTest {

    @BeforeAll
    static void init() throws IOException {
        GraphArgs args = builder()
                .ofSize(4)

                .build();

        init(args);
    }

    @Test
    public void adjacencyMatrixView() {
        //given

        //when
        double[][] graph = graphReflect.adjacencyMatrix();

        //then
        assertEquals(0, graph[0][0]);
        assertEquals(0, graph[0][1]);
        assertEquals(0, graph[0][2]);
        assertEquals(0, graph[0][3]);

        assertEquals(0, graph[1][0]);
        assertEquals(0, graph[1][1]);
        assertEquals(0, graph[1][2]);
        assertEquals(0, graph[1][3]);

        assertEquals(0, graph[2][0]);
        assertEquals(0, graph[2][1]);
        assertEquals(0, graph[2][2]);
        assertEquals(0, graph[2][3]);

        assertEquals(0, graph[3][0]);
        assertEquals(0, graph[3][1]);
        assertEquals(0, graph[3][2]);
        assertEquals(0, graph[3][3]);
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
        assertEquals(4, graph.size());

        assertEquals(0, graph.get(0).size());

        assertEquals(0, graph.get(1).size());

        assertEquals(0, graph.get(2).size());

        assertEquals(0, graph.get(3).size());
    }

    @Test
    public void adjacencyListAsMap() {
        //given

        //when
        Map<Integer, List<Object>> graph = graphReflect.adjacencyListAsMap();

        //then
        assertEquals(4, graph.size());

        assertEquals(0, graph.get(0).size());

        assertEquals(0, graph.get(1).size());

        assertEquals(0, graph.get(2).size());

        assertEquals(0, graph.get(3).size());
    }

    @Test
    public void adjacencyListOfEdges() {
        //given

        //when
        List<List<Object>> graph = graphReflect.adjacencyListOfEdges();

        //then
        assertEquals(4, graph.size());

        assertEquals(0, graph.get(0).size());

        assertEquals(0, graph.get(1).size());

        assertEquals(0, graph.get(2).size());

        assertEquals(0, graph.get(3).size());
    }

}
