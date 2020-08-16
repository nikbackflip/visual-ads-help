package com.backflip.vadsh.ds.graph.analyzer;

import com.backflip.vadsh.ds.graph.Config;
import com.backflip.vadsh.ds.graph.Graph;

public class CompleteAnalyzer implements Analyzer {

    @Override
    public boolean analyze(Graph graph, Config config) {
        if (config.isSelfLoopsAllowed()) {
            return graph.edgeList().size() == graph.n() * graph.n();
        } else {
            return graph.edgeList().size() == graph.n() * (graph.n() - 1);
        }
    }

}
