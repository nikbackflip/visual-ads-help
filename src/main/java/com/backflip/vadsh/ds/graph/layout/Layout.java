package com.backflip.vadsh.ds.graph.layout;

import com.backflip.vadsh.ds.graph.Config;
import com.backflip.vadsh.ds.graph.Graph;
import com.backflip.vadsh.ds.graph.analyzer.AnalyticDefinition;

import java.util.Map;

public interface Layout {

    static Layout getLayout(Graph graph, Config config, int x, int y) {
        if (AnalyticDefinition.DAG.analyze(graph, config)) return new DagLayout(graph, config, x, y);
        if (AnalyticDefinition.COMPLETE.analyze(graph, config)) return new CompleteLayout(graph, config, x, y);
        return new RandomLayout(graph, config, x, y);
    }

    Map<Integer, Coordinates> layout();

}
