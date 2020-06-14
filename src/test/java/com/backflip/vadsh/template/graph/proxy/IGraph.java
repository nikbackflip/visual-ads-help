package com.backflip.vadsh.template.graph.proxy;

import java.util.List;
import java.util.Map;

public interface IGraph {

    String getNodeName(Integer id);

    int nodesCount();

    double[][] adjacencyMatrix();

    List<Object> edgeList();

    Object[] edgeArray();

    List<List<Integer>> adjacencyList();

    Map<Integer, List<Object>> adjacencyListAsMap();

    List<List<Object>> adjacencyListOfEdges();

}
