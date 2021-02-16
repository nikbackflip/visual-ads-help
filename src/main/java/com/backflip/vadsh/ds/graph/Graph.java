package com.backflip.vadsh.ds.graph;

import java.util.*;
import java.util.stream.IntStream;

import static com.backflip.vadsh.ds.graph.Edge.edge;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

public class Graph {

    public Graph(List<Edge> edges, int n) {
        this.edgeList = edges;
        this.n = n;
    }

    public static Graph graphFromMatrix(double[][] matrix) {
        int n = matrix.length;
        List<Edge> edges = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] != 0) {
                    edges.add(edge(i, j, matrix[i][j]));
                }
            }
        }
        return new Graph(edges, n);
    }

    private final List<Edge> edgeList;
    private final int n;

    public int n() {
        return n;
    }

    public double[][] adjacencyMatrix() {
        double[][] graph = new double[n][n];
        edgeList.forEach(e -> graph[e.from()][e.to()] = e.weight());
        return graph;
    }

    public List<Edge> edgeList() {
        return new ArrayList<>(edgeList);
    }

    public Edge[] edgeArray() {
        return edgeList.toArray(new Edge[0]);
    }

    public List<List<Integer>> adjacencyList() {
        Map<Integer, List<Edge>> groupedEdges = edgeList.stream()
                .collect(groupingBy(Edge::from));

        return IntStream.range(0, n)
                .mapToObj(i -> {
                    List<Edge> list = groupedEdges.getOrDefault(i, emptyList());
                    return list.stream().map(Edge::to).collect(toList());
                })
                .collect(toList());
    }

    public Map<Integer, List<Edge>> adjacencyListAsMap() {
        Map<Integer, List<Edge>> groupedEdges = edgeList.stream()
                .collect(groupingBy(Edge::from));

        Map<Integer, List<Edge>> result = new HashMap<>();
        IntStream.range(0, n)
                .forEach(i -> {
                    List<Edge> list = groupedEdges.getOrDefault(i, emptyList());
                    result.put(i, list);
                });

        return result;
    }

    public List<List<Edge>> adjacencyListOfEdges() {
        Map<Integer, List<Edge>> groupedEdges = edgeList.stream()
                .collect(groupingBy(Edge::from));

        return IntStream.range(0, n)
                .mapToObj(i -> groupedEdges.getOrDefault(i, emptyList()))
                .collect(toList());
    }

}
