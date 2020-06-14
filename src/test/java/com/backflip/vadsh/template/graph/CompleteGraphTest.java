package com.backflip.vadsh.template.graph;

import com.backflip.vadsh.template.graph.proxy.IEdge;
import org.joor.Reflect;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CompleteGraphTest extends AbstractGraphTest {

    @BeforeAll
    static void init() throws IOException {
        Map<String, String> args = Map.of(
                "edgesList", "" +
                        "add(new Edge(0, 1, 1));" +
                        "add(new Edge(0, 2, 1));" +
                        "add(new Edge(0, 3, 1));" +

                        "add(new Edge(1, 0, 1));" +
                        "add(new Edge(1, 2, 1));" +
                        "add(new Edge(1, 3, 1));" +

                        "add(new Edge(2, 0, 1));" +
                        "add(new Edge(2, 1, 1));" +
                        "add(new Edge(2, 3, 1));" +

                        "add(new Edge(3, 0, 1));" +
                        "add(new Edge(3, 1, 1));" +
                        "add(new Edge(3, 2, 1));"
                ,
                "nodeIdToNameMap", "" +
                        "put(0, \"0\");" +
                        "put(1, \"1\");" +
                        "put(2, \"2\");" +
                        "put(3, \"3\");"
        );
        init(args);
    }

    @Test
    public void adjacencyMatrixView() {
        //given

        //when
        double[][] graph = graphReflect.adjacencyMatrix();

        //then
        assertEquals(0, graph[0][0]);
        assertEquals(1, graph[0][1]);
        assertEquals(1, graph[0][2]);
        assertEquals(1, graph[0][3]);

        assertEquals(1, graph[1][0]);
        assertEquals(0, graph[1][1]);
        assertEquals(1, graph[1][2]);
        assertEquals(1, graph[1][3]);

        assertEquals(1, graph[2][0]);
        assertEquals(1, graph[2][1]);
        assertEquals(0, graph[2][2]);
        assertEquals(1, graph[2][3]);

        assertEquals(1, graph[3][0]);
        assertEquals(1, graph[3][1]);
        assertEquals(1, graph[3][2]);
        assertEquals(0, graph[3][3]);
    }

    @Test
    public void edgeList() {
        //given

        //when
        List<?> graph = graphReflect.edgeList();

        //then
        assertEquals(12, graph.size());

        IEdge edge = Reflect.on(graph.get(0)).as(IEdge.class);
        assertEquals(0, edge.from());
        assertEquals(1, edge.to());
        assertEquals(1, edge.weight());

        edge = Reflect.on(graph.get(1)).as(IEdge.class);
        assertEquals(0, edge.from());
        assertEquals(2, edge.to());
        assertEquals(1, edge.weight());

        edge = Reflect.on(graph.get(2)).as(IEdge.class);
        assertEquals(0, edge.from());
        assertEquals(3, edge.to());
        assertEquals(1, edge.weight());

        edge = Reflect.on(graph.get(3)).as(IEdge.class);
        assertEquals(1, edge.from());
        assertEquals(0, edge.to());
        assertEquals(1, edge.weight());

        edge = Reflect.on(graph.get(4)).as(IEdge.class);
        assertEquals(1, edge.from());
        assertEquals(2, edge.to());
        assertEquals(1, edge.weight());

        edge = Reflect.on(graph.get(5)).as(IEdge.class);
        assertEquals(1, edge.from());
        assertEquals(3, edge.to());
        assertEquals(1, edge.weight());

        edge = Reflect.on(graph.get(6)).as(IEdge.class);
        assertEquals(2, edge.from());
        assertEquals(0, edge.to());
        assertEquals(1, edge.weight());

        edge = Reflect.on(graph.get(7)).as(IEdge.class);
        assertEquals(2, edge.from());
        assertEquals(1, edge.to());
        assertEquals(1, edge.weight());

        edge = Reflect.on(graph.get(8)).as(IEdge.class);
        assertEquals(2, edge.from());
        assertEquals(3, edge.to());
        assertEquals(1, edge.weight());

        edge = Reflect.on(graph.get(9)).as(IEdge.class);
        assertEquals(3, edge.from());
        assertEquals(0, edge.to());
        assertEquals(1, edge.weight());

        edge = Reflect.on(graph.get(10)).as(IEdge.class);
        assertEquals(3, edge.from());
        assertEquals(1, edge.to());
        assertEquals(1, edge.weight());

        edge = Reflect.on(graph.get(11)).as(IEdge.class);
        assertEquals(3, edge.from());
        assertEquals(2, edge.to());
        assertEquals(1, edge.weight());
    }

    @Test
    public void edgeArray() {
        //given

        //when
        Object[] graph = graphReflect.edgeArray();

        //then
        assertEquals(12, graph.length);

        IEdge edge = Reflect.on(graph[0]).as(IEdge.class);
        assertEquals(0, edge.from());
        assertEquals(1, edge.to());
        assertEquals(1, edge.weight());

        edge = Reflect.on(graph[1]).as(IEdge.class);
        assertEquals(0, edge.from());
        assertEquals(2, edge.to());
        assertEquals(1, edge.weight());

        edge = Reflect.on(graph[2]).as(IEdge.class);
        assertEquals(0, edge.from());
        assertEquals(3, edge.to());
        assertEquals(1, edge.weight());

        edge = Reflect.on(graph[3]).as(IEdge.class);
        assertEquals(1, edge.from());
        assertEquals(0, edge.to());
        assertEquals(1, edge.weight());

        edge = Reflect.on(graph[4]).as(IEdge.class);
        assertEquals(1, edge.from());
        assertEquals(2, edge.to());
        assertEquals(1, edge.weight());

        edge = Reflect.on(graph[5]).as(IEdge.class);
        assertEquals(1, edge.from());
        assertEquals(3, edge.to());
        assertEquals(1, edge.weight());

        edge = Reflect.on(graph[6]).as(IEdge.class);
        assertEquals(2, edge.from());
        assertEquals(0, edge.to());
        assertEquals(1, edge.weight());

        edge = Reflect.on(graph[7]).as(IEdge.class);
        assertEquals(2, edge.from());
        assertEquals(1, edge.to());
        assertEquals(1, edge.weight());

        edge = Reflect.on(graph[8]).as(IEdge.class);
        assertEquals(2, edge.from());
        assertEquals(3, edge.to());
        assertEquals(1, edge.weight());

        edge = Reflect.on(graph[9]).as(IEdge.class);
        assertEquals(3, edge.from());
        assertEquals(0, edge.to());
        assertEquals(1, edge.weight());

        edge = Reflect.on(graph[10]).as(IEdge.class);
        assertEquals(3, edge.from());
        assertEquals(1, edge.to());
        assertEquals(1, edge.weight());

        edge = Reflect.on(graph[11]).as(IEdge.class);
        assertEquals(3, edge.from());
        assertEquals(2, edge.to());
        assertEquals(1, edge.weight());
    }

    @Test
    public void adjacencyList() {
        //given

        //when
        List<List<Integer>> graph = graphReflect.adjacencyList();

        //then
        assertEquals(4, graph.size());

        assertEquals(3, graph.get(0).size());
        assertEquals(1, graph.get(0).get(0));
        assertEquals(2, graph.get(0).get(1));
        assertEquals(3, graph.get(0).get(2));

        assertEquals(3, graph.get(1).size());
        assertEquals(0, graph.get(1).get(0));
        assertEquals(2, graph.get(1).get(1));
        assertEquals(3, graph.get(1).get(2));

        assertEquals(3, graph.get(2).size());
        assertEquals(0, graph.get(2).get(0));
        assertEquals(1, graph.get(2).get(1));
        assertEquals(3, graph.get(2).get(2));

        assertEquals(3, graph.get(3).size());
        assertEquals(0, graph.get(3).get(0));
        assertEquals(1, graph.get(3).get(1));
        assertEquals(2, graph.get(3).get(2));
    }

    @Test
    public void adjacencyListAsMap() {
        //given

        //when
        Map<Integer, List<Object>> graph = graphReflect.adjacencyListAsMap();

        //then
        assertEquals(4, graph.size());

        assertEquals(3, graph.get(0).size());
        IEdge edge = Reflect.on(graph.get(0).get(0)).as(IEdge.class);
        assertEquals(0, edge.from());
        assertEquals(1, edge.to());
        assertEquals(1, edge.weight());
        edge = Reflect.on(graph.get(0).get(1)).as(IEdge.class);
        assertEquals(0, edge.from());
        assertEquals(2, edge.to());
        assertEquals(1, edge.weight());
        edge = Reflect.on(graph.get(0).get(2)).as(IEdge.class);
        assertEquals(0, edge.from());
        assertEquals(3, edge.to());
        assertEquals(1, edge.weight());

        assertEquals(3, graph.get(1).size());
        edge = Reflect.on(graph.get(1).get(0)).as(IEdge.class);
        assertEquals(1, edge.from());
        assertEquals(0, edge.to());
        assertEquals(1, edge.weight());
        edge = Reflect.on(graph.get(1).get(1)).as(IEdge.class);
        assertEquals(1, edge.from());
        assertEquals(2, edge.to());
        assertEquals(1, edge.weight());
        edge = Reflect.on(graph.get(1).get(2)).as(IEdge.class);
        assertEquals(1, edge.from());
        assertEquals(3, edge.to());
        assertEquals(1, edge.weight());

        assertEquals(3, graph.get(2).size());
        edge = Reflect.on(graph.get(2).get(0)).as(IEdge.class);
        assertEquals(2, edge.from());
        assertEquals(0, edge.to());
        assertEquals(1, edge.weight());
        edge = Reflect.on(graph.get(2).get(1)).as(IEdge.class);
        assertEquals(2, edge.from());
        assertEquals(1, edge.to());
        assertEquals(1, edge.weight());
        edge = Reflect.on(graph.get(2).get(2)).as(IEdge.class);
        assertEquals(2, edge.from());
        assertEquals(3, edge.to());
        assertEquals(1, edge.weight());

        assertEquals(3, graph.get(3).size());
        edge = Reflect.on(graph.get(3).get(0)).as(IEdge.class);
        assertEquals(3, edge.from());
        assertEquals(0, edge.to());
        assertEquals(1, edge.weight());
        edge = Reflect.on(graph.get(3).get(1)).as(IEdge.class);
        assertEquals(3, edge.from());
        assertEquals(1, edge.to());
        assertEquals(1, edge.weight());
        edge = Reflect.on(graph.get(3).get(2)).as(IEdge.class);
        assertEquals(3, edge.from());
        assertEquals(2, edge.to());
        assertEquals(1, edge.weight());
    }

    @Test
    public void adjacencyListOfEdges() {
        //given

        //when
        List<List<Object>> graph = graphReflect.adjacencyListOfEdges();

        //then
        assertEquals(4, graph.size());

        assertEquals(3, graph.get(0).size());
        IEdge edge = Reflect.on(graph.get(0).get(0)).as(IEdge.class);
        assertEquals(0, edge.from());
        assertEquals(1, edge.to());
        assertEquals(1, edge.weight());
        edge = Reflect.on(graph.get(0).get(1)).as(IEdge.class);
        assertEquals(0, edge.from());
        assertEquals(2, edge.to());
        assertEquals(1, edge.weight());
        edge = Reflect.on(graph.get(0).get(2)).as(IEdge.class);
        assertEquals(0, edge.from());
        assertEquals(3, edge.to());
        assertEquals(1, edge.weight());

        assertEquals(3, graph.get(1).size());
        edge = Reflect.on(graph.get(1).get(0)).as(IEdge.class);
        assertEquals(1, edge.from());
        assertEquals(0, edge.to());
        assertEquals(1, edge.weight());
        edge = Reflect.on(graph.get(1).get(1)).as(IEdge.class);
        assertEquals(1, edge.from());
        assertEquals(2, edge.to());
        assertEquals(1, edge.weight());
        edge = Reflect.on(graph.get(1).get(2)).as(IEdge.class);
        assertEquals(1, edge.from());
        assertEquals(3, edge.to());
        assertEquals(1, edge.weight());

        assertEquals(3, graph.get(2).size());
        edge = Reflect.on(graph.get(2).get(0)).as(IEdge.class);
        assertEquals(2, edge.from());
        assertEquals(0, edge.to());
        assertEquals(1, edge.weight());
        edge = Reflect.on(graph.get(2).get(1)).as(IEdge.class);
        assertEquals(2, edge.from());
        assertEquals(1, edge.to());
        assertEquals(1, edge.weight());
        edge = Reflect.on(graph.get(2).get(2)).as(IEdge.class);
        assertEquals(2, edge.from());
        assertEquals(3, edge.to());
        assertEquals(1, edge.weight());

        assertEquals(3, graph.get(3).size());
        edge = Reflect.on(graph.get(3).get(0)).as(IEdge.class);
        assertEquals(3, edge.from());
        assertEquals(0, edge.to());
        assertEquals(1, edge.weight());
        edge = Reflect.on(graph.get(3).get(1)).as(IEdge.class);
        assertEquals(3, edge.from());
        assertEquals(1, edge.to());
        assertEquals(1, edge.weight());
        edge = Reflect.on(graph.get(3).get(2)).as(IEdge.class);
        assertEquals(3, edge.from());
        assertEquals(2, edge.to());
        assertEquals(1, edge.weight());
    }


}
