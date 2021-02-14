package com.backflip.vadsh.ds.graph.generator;

import com.backflip.vadsh.ds.graph.Config;
import com.backflip.vadsh.ds.graph.Edge;
import com.backflip.vadsh.ds.graph.Graph;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static com.backflip.vadsh.ds.graph.Edge.edge;
import static com.backflip.vadsh.ds.graph.generator.GeneratorOption.*;

public class RandomGraphGenerator implements GraphGenerator {

    private final int size;
    private final GeneratorOption weight;
    private final GeneratorOption direction;
    private final GeneratorOption density;

    private final ThreadLocalRandom random = ThreadLocalRandom.current();

    protected RandomGraphGenerator(int size, GeneratorOption weight, GeneratorOption direction, GeneratorOption density) {
        this.size = size;
        this.weight = weight;
        this.direction = direction;
        this.density = density;
    }

    @Override
    public Graph getGraph() {
        double density = calculateDensity();
        int maxEdges = size * (size - 1);
        int edgesCount = (int) Math.floor(maxEdges * density);

        List<Edge> edges = new LinkedList<>();
        double[][] matrix = new double[size][size];

        if (direction == DIRECTED) {

            do {
                int fromNode = random.nextInt(size);
                int toNode = random.nextInt(size);
                if (fromNode == toNode) continue;
                if (matrix[fromNode][toNode] != 0) continue;

                edges.add(edge(fromNode, toNode, calculateWeight(weight)));
                matrix[fromNode][toNode] = 1;
                edgesCount--;

            } while (edgesCount > 0);

        } else {

            do {
                int fromNode = random.nextInt(size);
                int toNode = random.nextInt(size);
                double edgeWeight = calculateWeight(weight);
                if (fromNode == toNode) continue;
                if (matrix[fromNode][toNode] != 0) continue;

                edges.add(edge(fromNode, toNode, edgeWeight));
                edges.add(edge(toNode, fromNode, edgeWeight));
                matrix[fromNode][toNode] = 1;
                matrix[toNode][fromNode] = 1;
                edgesCount -= 2;

            } while (edgesCount > 1);
        }

        return new Graph(edges, size);
    }

    private double calculateWeight(GeneratorOption weight) {
        return weight == NOT_WEIGHTED ? 1.0 : random.nextInt(1, 20);
    }

    private double calculateDensity() {
        if (density == DENSE)  return random.nextDouble(0.6, 0.8);
        return random.nextDouble(0.1, 0.3);
    }

    @Override
    public Config getConfig() {
        return new Config(
                direction == GeneratorOption.DIRECTED,
                weight == GeneratorOption.WEIGHTED,
                false);
    }
}
