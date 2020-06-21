
import java.util.*;
import java.util.stream.IntStream;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.*;


public class Graph {

    public static final class Edge {
        public Edge(int from, int to, double weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        private final int from;
        private final int to;
        private final double weight;

        public int from() {
            return from;
        }

        public int to() {
            return to;
        }

        public double weight() {
            return weight;
        }
    }

    private final Map<Integer, String> nodeIdToNameMap = new HashMap<>() {{
        put(0, "0");
        put(1, "1");
        put(2, "2");
        put(3, "3");
        put(4, "4");
        put(5, "5");
        put(6, "6");
        put(7, "7");
        put(8, "8");
        put(9, "9");
        put(10, "10");

    }};

    private final List<Edge> edgesList = new ArrayList<>() {{
        add(new Edge(0, 3, 1.0));
        add(new Edge(3, 0, 1.0));
        add(new Edge(1, 2, 1.0));
        add(new Edge(2, 1, 1.0));
        add(new Edge(0, 2, 1.0));
        add(new Edge(2, 0, 1.0));
        add(new Edge(0, 1, 1.0));
        add(new Edge(1, 0, 1.0));
        add(new Edge(3, 2, 1.0));
        add(new Edge(2, 3, 1.0));
        add(new Edge(1, 3, 1.0));
        add(new Edge(3, 1, 1.0));
        add(new Edge(6, 5, 1.0));
        add(new Edge(7, 8, 1.0));
        add(new Edge(10, 9, 1.0));
        add(new Edge(9, 10, 1.0));

    }};

    private final int n = nodeIdToNameMap.size();

    public String getNodeName(Integer id) {
        return nodeIdToNameMap.get(id);
    }

    public int nodesCount() {
        return n;
    }

    public double[][] adjacencyMatrix() {
        double[][] graph = new double[n][n];
        edgesList.forEach(e -> graph[e.from()][e.to()] = e.weight());
        return graph;
    }

    public List<Edge> edgeList() {
        return new ArrayList<>(edgesList);
    }

    public Edge[] edgeArray() {
        return edgesList.toArray(new Edge[0]);
    }

    public List<List<Integer>> adjacencyList() {

        Map<Integer, List<Edge>> groupedEdges = edgesList.stream()
                .collect(groupingBy(Edge::from));

        return IntStream.range(0, n)
                .mapToObj(i -> {
                    List<Edge> list = groupedEdges.getOrDefault(i, emptyList());
                    return list.stream().map(Edge::to).collect(toList());
                })
                .collect(toList());
    }

    public Map<Integer, List<Edge>> adjacencyListAsMap() {
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

    public List<List<Edge>> adjacencyListOfEdges() {
        Map<Integer, List<Edge>> groupedEdges = edgesList.stream()
                .collect(groupingBy(Edge::from));

        return IntStream.range(0, n)
                .mapToObj(i -> groupedEdges.getOrDefault(i, emptyList()))
                .collect(toList());
    }

}
