package com.backflip.vadsh.template.graph;

import com.backflip.vadsh.template.graph.proxy.IEdge;
import com.backflip.vadsh.templates.graph.GraphArgs;
import org.joor.Reflect;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.backflip.vadsh.templates.graph.GraphArgs.builder;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimpleGraphTest extends AbstractGraphTest {

    @BeforeAll
    public static void init() throws IOException {
        GraphArgs args = builder()
                .withEdge(0, 2, 1)
                .withEdge(2, 1, -1)
                .withEdge(1, 3, 10)
                .withEdge(3, 0, 11)

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
        assertEquals(1, graph[0][2]);
        assertEquals(0, graph[0][3]);

        assertEquals(0, graph[1][0]);
        assertEquals(0, graph[1][1]);
        assertEquals(0, graph[1][2]);
        assertEquals(10, graph[1][3]);

        assertEquals(0, graph[2][0]);
        assertEquals(-1, graph[2][1]);
        assertEquals(0, graph[2][2]);
        assertEquals(0, graph[2][3]);

        assertEquals(11, graph[3][0]);
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
        assertEquals(4, graph.size());

        IEdge edge0 = Reflect.on(graph.get(0)).as(IEdge.class);
        assertEquals(0, edge0.from());
        assertEquals(2, edge0.to());
        assertEquals(1, edge0.weight());

        IEdge edge1 = Reflect.on(graph.get(1)).as(IEdge.class);
        assertEquals(2, edge1.from());
        assertEquals(1, edge1.to());
        assertEquals(-1, edge1.weight());

        IEdge edge2 = Reflect.on(graph.get(2)).as(IEdge.class);
        assertEquals(1, edge2.from());
        assertEquals(3, edge2.to());
        assertEquals(10, edge2.weight());

        IEdge edge3 = Reflect.on(graph.get(3)).as(IEdge.class);
        assertEquals(3, edge3.from());
        assertEquals(0, edge3.to());
        assertEquals(11, edge3.weight());
    }

    @Test
    public void edgeArray() {
        //given

        //when
        Object[] graph = graphReflect.edgeArray();

        //then
        assertEquals(4, graph.length);

        IEdge edge0 = Reflect.on(graph[0]).as(IEdge.class);
        assertEquals(0, edge0.from());
        assertEquals(2, edge0.to());
        assertEquals(1, edge0.weight());

        IEdge edge1 = Reflect.on(graph[1]).as(IEdge.class);
        assertEquals(2, edge1.from());
        assertEquals(1, edge1.to());
        assertEquals(-1, edge1.weight());

        IEdge edge2 = Reflect.on(graph[2]).as(IEdge.class);
        assertEquals(1, edge2.from());
        assertEquals(3, edge2.to());
        assertEquals(10, edge2.weight());

        IEdge edge3 = Reflect.on(graph[3]).as(IEdge.class);
        assertEquals(3, edge3.from());
        assertEquals(0, edge3.to());
        assertEquals(11, edge3.weight());
    }

    @Test
    public void adjacencyList() {
        //given

        //when
        List<List<Integer>> graph = graphReflect.adjacencyList();

        //then
        assertEquals(4, graph.size());

        assertEquals(1, graph.get(0).size());
        assertEquals(2, graph.get(0).get(0));

        assertEquals(1, graph.get(1).size());
        assertEquals(3, graph.get(1).get(0));

        assertEquals(1, graph.get(2).size());
        assertEquals(1, graph.get(2).get(0));

        assertEquals(1, graph.get(3).size());
        assertEquals(0, graph.get(3).get(0));
    }

    @Test
    public void adjacencyListAsMap() {
        //given

        //when
        Map<Integer, List<Object>> graph = graphReflect.adjacencyListAsMap();

        //then
        assertEquals(4, graph.size());

        assertEquals(1, graph.get(0).size());
        IEdge edge0 = Reflect.on(graph.get(0).get(0)).as(IEdge.class);
        assertEquals(0, edge0.from());
        assertEquals(2, edge0.to());
        assertEquals(1, edge0.weight());

        assertEquals(1, graph.get(1).size());
        IEdge edge1 = Reflect.on(graph.get(1).get(0)).as(IEdge.class);
        assertEquals(1, edge1.from());
        assertEquals(3, edge1.to());
        assertEquals(10, edge1.weight());

        assertEquals(1, graph.get(2).size());
        IEdge edge2 = Reflect.on(graph.get(2).get(0)).as(IEdge.class);
        assertEquals(2, edge2.from());
        assertEquals(1, edge2.to());
        assertEquals(-1, edge2.weight());

        assertEquals(1, graph.get(3).size());
        IEdge edge3 = Reflect.on(graph.get(3).get(0)).as(IEdge.class);
        assertEquals(3, edge3.from());
        assertEquals(0, edge3.to());
        assertEquals(11, edge3.weight());
    }

    @Test
    public void adjacencyListOfEdges() {
        //given

        //when
        List<List<Object>> graph = graphReflect.adjacencyListOfEdges();

        //then
        assertEquals(4, graph.size());

        assertEquals(1, graph.get(0).size());
        IEdge edge0 = Reflect.on(graph.get(0).get(0)).as(IEdge.class);
        assertEquals(0, edge0.from());
        assertEquals(2, edge0.to());
        assertEquals(1, edge0.weight());

        assertEquals(1, graph.get(1).size());
        IEdge edge1 = Reflect.on(graph.get(1).get(0)).as(IEdge.class);
        assertEquals(1, edge1.from());
        assertEquals(3, edge1.to());
        assertEquals(10, edge1.weight());

        assertEquals(1, graph.get(2).size());
        IEdge edge2 = Reflect.on(graph.get(2).get(0)).as(IEdge.class);
        assertEquals(2, edge2.from());
        assertEquals(1, edge2.to());
        assertEquals(-1, edge2.weight());

        assertEquals(1, graph.get(3).size());
        IEdge edge3 = Reflect.on(graph.get(3).get(0)).as(IEdge.class);
        assertEquals(3, edge3.from());
        assertEquals(0, edge3.to());
        assertEquals(11, edge3.weight());
    }

}
