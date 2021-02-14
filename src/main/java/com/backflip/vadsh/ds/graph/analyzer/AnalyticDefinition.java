package com.backflip.vadsh.ds.graph.analyzer;

import com.backflip.vadsh.ds.graph.Config;
import com.backflip.vadsh.ds.graph.Graph;

public enum AnalyticDefinition {

    DIRECTIONAL("directional", new DirectionalAnalyzer()),
    WEIGHTED("weighted", new WeightedAnalyzer()),
    COMPLETE("complete", new CompleteAnalyzer()),
    TREE("tree", new TreeAnalyzer()),
    DAG("DAG", new DagAnalyzer()),
    NEGATIVE_CYCLES("negative cycles", new NegativeCyclesAnalyzer()),
    DISCONNECTED("disconnected", new DisconnectedAnalyzer()),
    EULERIAN_PATH("eulerian path", new EulerianPathAnalyzer()),
    DENSE("dense", new DenseAnalyzer()),
    SPARSE("sparse", new SparseAnalyzer());

    AnalyticDefinition(String label, Analyzer analyzer) {
        this.label = label;
        this.analyzer = analyzer;
    }

    private final String label;
    private final Analyzer analyzer;

    public String label() {
        return label;
    }

    public boolean analyze(Graph graph, Config config) {
        return analyzer.analyze(graph, config);
    }

    public boolean analyzeAndNegate(Graph graph, Config config) {
        return !analyze(graph, config);
    }
}
