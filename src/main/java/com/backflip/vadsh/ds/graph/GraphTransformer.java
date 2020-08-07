package com.backflip.vadsh.ds.graph;

import java.util.*;
import java.util.stream.IntStream;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.*;

public class GraphTransformer {

    public static double[][] adjacencyMatrix(List<Edge> edgesList, int n) {
        double[][] graph = new double[n][n];
        edgesList.forEach(e -> graph[e.from()][e.to()] = e.weight());
        return graph;
    }

    public static List<Edge> edgeList(List<Edge> edgesList) {
        return new ArrayList<>(edgesList);
    }

    public static Edge[] edgeArray(List<Edge> edgesList) {
        return edgesList.toArray(new Edge[0]);
    }

    public static List<List<Integer>> adjacencyList(List<Edge> edgesList, int n) {
        Map<Integer, List<Edge>> groupedEdges = edgesList.stream()
                .collect(groupingBy(Edge::from));

        return IntStream.range(0, n)
                .mapToObj(i -> {
                    List<Edge> list = groupedEdges.getOrDefault(i, emptyList());
                    return list.stream().map(Edge::to).collect(toList());
                })
                .collect(toList());
    }

    public static Map<Integer, List<Edge>> adjacencyListAsMap(List<Edge> edgesList,  int n) {
        Map<Integer, List<Edge>> groupedEdges = edgesList.stream()
                .collect(groupingBy(Edge::from));

        Map<Integer, List<Edge>> result = new HashMap<>();
        IntStream.range(0, n)
                .forEach(i -> {
                    List<Edge> list = groupedEdges.getOrDefault(i, emptyList());
                    result.put(i, list);
                });

        return result;
    }

    public static List<List<Edge>> adjacencyListOfEdges(List<Edge> edgesList,  int n) {
        Map<Integer, List<Edge>> groupedEdges = edgesList.stream()
                .collect(groupingBy(Edge::from));

        return IntStream.range(0, n)
                .mapToObj(i -> groupedEdges.getOrDefault(i, emptyList()))
                .collect(toList());
    }

}
