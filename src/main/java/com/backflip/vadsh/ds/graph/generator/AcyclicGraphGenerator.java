package com.backflip.vadsh.ds.graph.generator;

import com.backflip.vadsh.ds.graph.Config;
import com.backflip.vadsh.ds.graph.Edge;
import com.backflip.vadsh.ds.graph.Graph;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static com.backflip.vadsh.ds.graph.Edge.edge;
import static com.backflip.vadsh.ds.graph.generator.GeneratorOption.*;

public class AcyclicGraphGenerator implements GraphGenerator {

    private final int size;
    private final GeneratorOption weight;
    private final GeneratorOption direction;

    private final ThreadLocalRandom random = ThreadLocalRandom.current();

    protected AcyclicGraphGenerator(int size, GeneratorOption weight, GeneratorOption direction) {
        this.size = size;
        this.weight = weight;
        this.direction = direction;
    }

    @Override
    public Graph getGraph() {

        if (direction == DIRECTED) {
            double density = calculateDensity();
            int maxEdges = ((size * size) - size) / 2;
            int edgesCount = calculateExpectedEdges(maxEdges, density);
            double[][] matrix = new double[size][size];

            boolean[] connected = new boolean[maxEdges];
            for (int i = 0; i < edgesCount; i++) {
                connected[i] = true;
            }
            shuffleArray(connected);

            int c = 0;
            for (int i = 1; i < size; i++) {
                for (int j = i + 1; j < size; j++) {
                    if (connected[c]) {
                        matrix[i][j] = calculateWeight();
                    }
                    c++;
                }
            }

            return Graph.graphFromMatrix(matrix);
        } else {
            double density = random.nextDouble(0.2, 0.9);
            int maxEdges = (size - 1);
            int edgesCount = calculateExpectedEdges(maxEdges, density);

            boolean[] connected = new boolean[size];
            List<Edge> edges = new LinkedList<>();

            while (edgesCount != 0) {
                int fromNode = random.nextInt(0, size);
                connected[fromNode] = true;
                if (!freeNodesLeft(connected)) break;

                int toNode = getNextDisconnected(connected);
                connected[toNode] = true;

                edges.add(edge(fromNode, toNode));
                edges.add(edge(toNode, fromNode));

                edgesCount--;
            }
            return new Graph(edges, size);
        }

    }

    private boolean freeNodesLeft(boolean[] connected) {
        for (boolean node : connected)
            if (!node) return true;
        return false;
    }

    private int getNextDisconnected(boolean[] connected) {
        int index = random.nextInt(0, size);
        while (connected[index]) {
            index++;
            if (index == size) index = 0;
        }
        return index;
    }

    private int calculateExpectedEdges(int maxEdges, double density) {
        return (int) Math.floor(maxEdges * density);
    }

    private double calculateWeight() {
        return weight == NOT_WEIGHTED ? 1.0 : random.nextInt(1, 20);
    }

    private double calculateDensity() {
        return random.nextDouble(0.1, 0.3);
    }

    @Override
    public Config getConfig() {
        return new Config(
                direction == GeneratorOption.DIRECTED,
                weight == GeneratorOption.WEIGHTED,
                false
        );
    }

    //Fisher–Yates
    private void shuffleArray(boolean[] array) {
        int index;
        for (int i = array.length - 1; i > 0; i--) {
            index = random.nextInt(i + 1);
            if (index != i) {
                array[index] ^= array[i];
                array[i] ^= array[index];
                array[index] ^= array[i];
            }
        }
    }
}
