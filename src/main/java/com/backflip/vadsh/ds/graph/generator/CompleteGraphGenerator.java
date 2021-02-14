package com.backflip.vadsh.ds.graph.generator;

import com.backflip.vadsh.ds.graph.Config;
import com.backflip.vadsh.ds.graph.Edge;
import com.backflip.vadsh.ds.graph.Graph;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static com.backflip.vadsh.ds.graph.Edge.edge;
import static com.backflip.vadsh.ds.graph.generator.GeneratorOption.*;

public class CompleteGraphGenerator implements GraphGenerator {

    private final int size;
    private final GeneratorOption weight;
    private final GeneratorOption direction;

    private final ThreadLocalRandom random = ThreadLocalRandom.current();

    protected CompleteGraphGenerator(int size, GeneratorOption weight, GeneratorOption direction) {
        this.size = size;
        this.weight = weight;
        this.direction = direction;
    }

    @Override
    public Graph getGraph() {
        List<Edge> edges = new LinkedList<>();

        for (int i = 0; i < size - 1; i++) {
            for (int j = i+1; j < size; j++) {
                double edgeWeight = calculateWeight(weight);
                edges.add(edge(i, j, edgeWeight));
                edges.add(edge(j, i, edgeWeight));
            }
        }
        return new Graph(edges, size);
    }

    private double calculateWeight(GeneratorOption weightOption) {
        return weightOption == NOT_WEIGHTED ? 1.0 : random.nextInt(1, 20);
    }

    @Override
    public Config getConfig() {
        return new Config(
                direction == GeneratorOption.DIRECTED,
                weight == GeneratorOption.WEIGHTED,
                false);
    }
}
