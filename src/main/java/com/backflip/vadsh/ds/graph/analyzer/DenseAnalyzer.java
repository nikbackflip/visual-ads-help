package com.backflip.vadsh.ds.graph.analyzer;

import com.backflip.vadsh.ds.graph.Config;
import com.backflip.vadsh.ds.graph.Graph;

public class DenseAnalyzer implements Analyzer {

    @Override
    public boolean analyze(Graph graph, Config config) {
        int size = graph.n();
        int edgesCount = graph.edgeList().size();
        double maxEdges = config.isSelfLoopsAllowed() ? size * size : size * (size - 1);
        double density = edgesCount / maxEdges;
        return edgesCount == maxEdges || density >= 0.5;
    }

}
