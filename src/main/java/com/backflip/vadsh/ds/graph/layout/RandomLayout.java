package com.backflip.vadsh.ds.graph.layout;

import com.backflip.vadsh.ds.graph.Config;
import com.backflip.vadsh.ds.graph.Graph;

import java.util.*;
import java.util.stream.IntStream;

import static com.backflip.vadsh.util.Collectors.toShuffledList;

public class RandomLayout extends HexGridLayout {

    private final Graph graph;
    private final Config config;

    private int[] pos;
    private int[] gridPos;
    private List<TrackedPair> pairs;
    private int overallSum;


    public RandomLayout(Graph graph, Config config, int x, int y) {
        super(x, y);
        this.graph = graph;
        this.config = config;
    }

    public Map<Integer, Coordinates> layout() {
        int gridSize = gridX * gridY;
        int n = graph.n();

        //null if graph does not fit
        //TODO DO SMTH WITH THIS!!!
        //perhaps create a completely random layout as in frontend
        if (n > gridSize) {
            return null;
        }

        pos = new int[n]; //position of each node on a grid
        gridPos = new int[gridSize]; //each grid cell's content, -1 if empty
        for (int i = 0; i < gridSize; i++) gridPos[i] = -1;

        //Place Nodes Randomly
        List<Integer> shuffled = IntStream.range(0, gridSize)
                .boxed().collect(toShuffledList());
        for (int i = 0; i < n; i++) {
            pos[i] = shuffled.get(i);
            gridPos[pos[i]] = i;
        }

        //Create tracked Nodes Pairs
        pairs = new LinkedList<>();
        double[][] matrix = graph.adjacencyMatrix();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] != 0) {
                    pairs.add(new TrackedPair(i, j, distanceOnGrid(pos[i], pos[j])));
                    matrix[j][i] = 0;
                }
            }
        }

        if (pairs.size() == 0) {
            return toCoordinates(pos);
        }

        //Calculate initial overall sum
        overallSum = calcOverallSum();

        //Improve overall sum
        while (canImprove()) {
            TrackedPair pair = nextPair();
            if (nodeMovingImproves(pair.from) || nodeMovingImproves(pair.to)) {
                improvedInThisIteration = true;
            }
        }

        return toCoordinates(pos);
    }

    private boolean nodeMovingImproves(int node) {
        int nodePos = pos[node];
        List<Integer> neighbours = getNeighboursOnGris(pos[node]);

        for (Integer neighPos: neighbours) {
            if (neighbourSwapImproves(node, nodePos, neighPos)) return true;
        }
        return false;
    }

    private boolean neighbourSwapImproves(int you, int yourPos, Integer neighPos) {
        int neigh = gridPos[neighPos];

        pos[you] = neighPos;

        if (neigh != -1) {
            pos[neigh] = yourPos;
        }

        gridPos[yourPos] = neigh;
        gridPos[neighPos] = you;

        int newSum = recalcDistances(you, neigh);

        if (newSum < overallSum) {
            overallSum = newSum;
            return true;
        } else {
            pos[you] = yourPos;

            if (neigh != -1) {
                pos[neigh] = neighPos;
            }

            gridPos[yourPos] = you;
            gridPos[neighPos] = neigh;
            recalcDistances(you, neigh);
        }
        return false;
    }

    //------------------------------------------------------------------------

    int lastPair = -1;
    boolean improvedInThisIteration = false;
    private boolean canImprove() {
        return lastPair != pairs.size() - 1 || improvedInThisIteration;
    }

    private TrackedPair nextPair() {
        if (lastPair == pairs.size() - 1) {
            improvedInThisIteration = false;
            lastPair = -1;
        }
        return pairs.get(++lastPair);
    }

    //------------------------------------------------------------------------

    private int calcOverallSum() {
        return pairs.stream().mapToInt(TrackedPair::distance).sum();
    }

    private int recalcDistances(int movedNode0, int movedNode1) {
        pairs.stream()
                .filter(p -> p.from == movedNode0 || p.to == movedNode0 || p.from == movedNode1 || p.to == movedNode1)
                .forEach(p -> p.distance = distanceOnGrid(pos[p.from], pos[p.to]));
        return calcOverallSum();
    }

    private static class TrackedPair {
        final int from;
        final int to;

        public int distance() {
            return distance;
        }

        int distance;

        public TrackedPair(int from, int to, int distance) {
            this.from = from;
            this.to = to;
            this.distance = distance;
        }
    }

}