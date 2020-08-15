package com.backflip.vadsh.ds.graph.analyzer;

import com.backflip.vadsh.ds.graph.Config;
import com.backflip.vadsh.ds.graph.Graph;

public enum Analytic {

    DIRECTIONAL("directional", new DirectionalAnalyzer()),
    WEIGHTED("weighted", new WeightedAnalyzer()),
    COMPLETE("complete", new CompleteAnalyzer()),
    TREE("tree", new TreeAnalyzer()),
    DAG("DAG", new DagAnalyzer());

    Analytic(String label, Analyzer analyzer) {
        this.label = label;
        this.analyzer = analyzer;
    }

    private String label;
    private Analyzer analyzer;

    public String label() {
        return label;
    }

    public boolean analyze(Graph graph, Config config) {
        return analyzer.analyze(graph, config);

    }
}
