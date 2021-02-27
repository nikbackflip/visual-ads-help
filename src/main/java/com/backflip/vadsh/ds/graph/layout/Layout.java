package com.backflip.vadsh.ds.graph.layout;

import com.backflip.vadsh.ds.graph.Config;
import com.backflip.vadsh.ds.graph.Graph;
import com.backflip.vadsh.ds.graph.analyzer.AnalyticDefinition;

import java.util.Map;

public interface Layout {

    static Layout getLayout(Graph graph, Config config) {
        if (AnalyticDefinition.DAG.analyze(graph, config)) return new DagLayout(graph, config);
        if (AnalyticDefinition.COMPLETE.analyze(graph, config)) return new CompleteLayout(graph, config);
        return new RandomLayout(graph, config);
    }

    Map<Integer, Coordinates> layout(int x, int y);

}
