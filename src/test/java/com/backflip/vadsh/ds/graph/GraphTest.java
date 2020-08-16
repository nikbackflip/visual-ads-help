package com.backflip.vadsh.ds.graph;

import com.backflip.vadsh.test.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsEmptyIterable.emptyIterable;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.core.AnyOf.anyOf;
import static org.hamcrest.core.Is.is;

import java.util.List;
import java.util.Map;


public class GraphTest {

    @ParameterizedTest
    @ArgumentsSource(AdjacencyMatrixArgumentsProvider.class)
    public void adjacencyMatrixTest(List<Edge> edges, int size, double[][] expected) {

        //given
        Graph graph = new Graph(edges, size);

        //when
        double[][] result = graph.adjacencyMatrix();

        //then
        assertThat(result, is(expected));

    }

    @ParameterizedTest
    @ArgumentsSource(EdgeListArgumentsProvider.class)
    public void edgeListTest(List<Edge> edges, int size, List<Edge> expected) {

        //given
        Graph graph = new Graph(edges, size);

        //when
        List<Edge> result = graph.edgeList();

        //then
        assertThat(result, anyOf(emptyIterable(), contains(expected.toArray())));

    }

    @ParameterizedTest
    @ArgumentsSource(EdgeArrayArgumentsProvider.class)
    public void edgeArrayTest(List<Edge> edges, int size, Edge[] expected) {

        //given
        Graph graph = new Graph(edges, size);

        //when
        Edge[] result = graph.edgeArray();

        //then
        assertThat(result, is(expected));

    }

    @ParameterizedTest
    @ArgumentsSource(AdjacencyListArgumentsProvider.class)
    public void adjacencyListTest(List<Edge> edges, int size, List<List<Integer>> expected) {

        //given
        Graph graph = new Graph(edges, size);

        //when
        List<List<Integer>> result = graph.adjacencyList();

        //then
        for (int i = 0; i < expected.size(); i++) {
            assertThat(result.get(i), anyOf(emptyIterable(), contains(expected.get(i).toArray())));
        }

    }

    @ParameterizedTest
    @ArgumentsSource(AdjacencyListAsMapArgumentsProvider.class)
    public void adjacencyListAsMapTest(List<Edge> edges, int size, Map<Integer, List<Edge>> expected) {

        //given
        Graph graph = new Graph(edges, size);

        //when
        Map<Integer, List<Edge>> result = graph.adjacencyListAsMap();

        //then
        for (Map.Entry<Integer, List<Edge>> entry: expected.entrySet()) {
            assertThat(result.get(entry.getKey()), anyOf(emptyIterable(), contains(entry.getValue().toArray())));
        }

    }

    @ParameterizedTest
    @ArgumentsSource(AdjacencyListOfEdgesArgumentsProvider.class)
    public void adjacencyListOfEdgesTest(List<Edge> edges, int size, List<List<Edge>> expected) {

        //given
        Graph graph = new Graph(edges, size);

        //when
        List<List<Edge>> result = graph.adjacencyListOfEdges();

        //then
        for (int i = 0; i < expected.size(); i++) {
            assertThat(result.get(i), anyOf(emptyIterable(), contains(expected.get(i).toArray())));
        }
    }

}
