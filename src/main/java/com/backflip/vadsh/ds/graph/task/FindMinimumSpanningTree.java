package com.backflip.vadsh.ds.graph.task;

import com.backflip.vadsh.ds.graph.Config;
import com.backflip.vadsh.ds.graph.Edge;
import com.backflip.vadsh.ds.graph.Graph;

import java.util.*;

import static com.backflip.vadsh.ds.graph.task.TaskResult.successEdges;

public class FindMinimumSpanningTree implements Task {

    static final class UnionFind {

        int[] array;

        UnionFind(int n) {
            array = new int[n];
            for (int i = 0; i < n; i++) {
                array[i] = i;
            }
        }

        void unify(int x, int y) {
            if (find(x) == find(y)) {
                return;
            }
            if (find(y) == y) {
                array[y] = x;
            }
            unify(x, find(y));
        }

        int find(int x) {
            if (array[x] == x) {
                return x;
            }
            return find(array[x]);
        }

    }

    @Override
    public TaskResult execute(Graph graphInput, Config config, Map<String, Integer> params) {
        List<Edge> graph = graphInput.edgeList();
        int n = graphInput.n();

        List<Edge> mst = new ArrayList<>();

        graph.sort(Comparator.comparing(Edge::weight));
        UnionFind uf = new UnionFind(n);

        for (Edge e: graph) {
            int from = e.from();
            int to = e.to();

            if (uf.find(from) != uf.find(to)) {
                uf.unify(from, to);
                mst.add(e);
            }
        }

        return  successEdges(mst);
    }
}