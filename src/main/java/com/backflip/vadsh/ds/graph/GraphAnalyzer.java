package com.backflip.vadsh.ds.graph;

import java.util.List;

public class GraphAnalyzer {

    public static boolean complete(List<Edge> edges, int n, Boolean selfLoopsAllowed) {
        if (selfLoopsAllowed) {
            return edges.size() == n * n;
        } else {
            return edges.size() == n * (n - 1);
        }
    }
}
