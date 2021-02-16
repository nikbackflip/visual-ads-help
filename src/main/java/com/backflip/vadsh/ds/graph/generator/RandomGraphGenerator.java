package com.backflip.vadsh.ds.graph.generator;

import com.backflip.vadsh.ds.graph.Config;
import com.backflip.vadsh.ds.graph.Graph;

import java.util.concurrent.ThreadLocalRandom;

import static com.backflip.vadsh.ds.graph.Graph.graphFromMatrix;
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
        int edgesCount = calculateExpectedEdges(maxEdges, density);
        double[][] matrix = new double[size][size];

        if (direction == DIRECTED) {
            boolean[] connected = new boolean[size*(size-1)];
            for (int i = 0; i < edgesCount; i++) {
                connected[i] = true;
            }
            shuffleArray(connected);

            int c = 0;
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (i == j) continue;
                    if (connected[c]) {
                        matrix[i][j] = calculateWeight(weight);
                    }
                    c++;
                }
            }
        } else {
            edgesCount = edgesCount / 2;
            boolean[] connected = new boolean[size*(size-1) / 2];
            for (int i = 0; i < edgesCount; i++) {
                connected[i] = true;
            }
            shuffleArray(connected);

            int c = 0;
            for (int i = 1; i < size; i++) {
                for (int j = 0; j < i; j++) {
                    if (connected[c]) {
                        double calcWeight = calculateWeight(weight);
                        matrix[i][j] = calcWeight;
                        matrix[j][i] = calcWeight;
                    }
                    c++;
                }
            }
        }
        return graphFromMatrix(matrix);
    }

    private int calculateExpectedEdges(int maxEdges, double density) {
        if (this.density == DENSE) return (int) Math.ceil(maxEdges * density);
        else return (int) Math.floor(maxEdges * density);
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
