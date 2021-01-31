package com.backflip.vadsh.ds.graph.analyzer;

import com.backflip.vadsh.ds.graph.Config;
import com.backflip.vadsh.ds.graph.Edge;
import com.backflip.vadsh.ds.graph.Graph;

import java.util.List;

public class EulerianPathAnalyzer implements Analyzer {

    @Override
    public boolean analyze(Graph graph, Config config) {

        List<Edge> edges = graph.edgeList();
        int n = graph.n();

        if (edges.size() == 0) return true;

        int[] in = new int[n];
        int[] out = new int[n];

        for (Edge ed: edges) {
            out[ed.from()]++;
            in[ed.to()]++;
        }

        boolean solvable = true;
        if (config.isGraphDirectional()) {
            int startNode = -1;
            int endNode = -1;
            for (int i = 0; i < n; i++) {
                if (out[i] - in[i] == 1) {
                    if (startNode == -1) startNode = i;
                    else solvable = false;
                } else if (out[i] - in[i] == -1) {
                    if (endNode == -1) endNode = i;
                    else solvable = false;
                } else if (out[i] != in[i]) solvable = false;
            }
            return solvable;
        } else {
            int oddDegrees = 0;
            for (int i = 0; i < n; i++) {
                if (out[i] % 2 != 0) oddDegrees++;
            }
            if (oddDegrees != 0 && oddDegrees != 2) solvable = false;
            return solvable;
        }
    }
}
