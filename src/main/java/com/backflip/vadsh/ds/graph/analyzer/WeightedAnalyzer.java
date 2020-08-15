package com.backflip.vadsh.ds.graph.analyzer;

import com.backflip.vadsh.ds.graph.Config;
import com.backflip.vadsh.ds.graph.Graph;

public class WeightedAnalyzer implements Analyzer {

    @Override
    public boolean analyze(Graph graph, Config config) {
        return config.isGraphWeighted();
    }

}
