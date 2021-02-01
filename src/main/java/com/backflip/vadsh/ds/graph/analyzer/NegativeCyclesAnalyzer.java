package com.backflip.vadsh.ds.graph.analyzer;

import com.backflip.vadsh.ds.graph.Config;
import com.backflip.vadsh.ds.graph.Edge;
import com.backflip.vadsh.ds.graph.Graph;

import java.util.List;

public class NegativeCyclesAnalyzer implements Analyzer {

    @Override
    public boolean analyze(Graph graph, Config config) {
        List<Edge> edges = graph.edgeList();
        int n = graph.n();

        for (int i = 0; i < n; i++) {
            edges.add(new Edge(n, i, 0.0));
        }
        n++;

        float[] dist = new float[n];
        for (int i = 0; i < n; i++) {
            dist[i] = Float.POSITIVE_INFINITY;
        }
        dist[n - 1] = 0;


        for (int i = 0; i < n - 1; i++) {
            for (Edge e : edges) {
                if (dist[e.from()] + e.weight() < dist[e.to()]) {
                    dist[e.to()] = dist[e.from()] + (float) e.weight();
                }
            }
        }

        for (int i = 0; i < n - 1; i++) {
            for (Edge e : edges) {
                if (dist[e.from()] + e.weight() < dist[e.to()]) {
                    return true;
                }
            }
        }

        return false;
    }
}
