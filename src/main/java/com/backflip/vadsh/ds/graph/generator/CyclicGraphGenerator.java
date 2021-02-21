package com.backflip.vadsh.ds.graph.generator;

import com.backflip.vadsh.ds.graph.Config;
import com.backflip.vadsh.ds.graph.Edge;
import com.backflip.vadsh.ds.graph.Graph;

import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

import static com.backflip.vadsh.ds.graph.Edge.edge;
import static com.backflip.vadsh.ds.graph.generator.GeneratorOption.*;

public class CyclicGraphGenerator implements GraphGenerator {

    private final int size;
    private final GeneratorOption weight;
    private final GeneratorOption direction;
    private final GeneratorOption density;

    private final ThreadLocalRandom random = ThreadLocalRandom.current();

    protected CyclicGraphGenerator(int size, GeneratorOption weight, GeneratorOption direction, GeneratorOption density) {
        this.size = size;
        this.weight = weight;
        this.direction = direction;
        this.density = density;
    }

    private Graph getGraph() {
        LinkedList<Edge> edges = new LinkedList<>();
        int cycleSize = calculateCycleSize();

        boolean[] connected = new boolean[size];
        for (int i = 0; i < cycleSize; i++) {
            connected[i] = true;
        }
        shuffleArray(connected);

        int[] cycle = new int[cycleSize];
        int j = 0;
        for (int i = 0; i < size; i++) {
            if (connected[i]) {
                cycle[j] = i;
                j++;
            }
        }

        if (cycle.length == 0) {
            addEdge(edges, 0, 0);
        } else {
            int prevNode = cycle[0];
            for (int i = 1; i < cycleSize; i++) {
                addEdge(edges, prevNode, cycle[i]);
                prevNode = cycle[i];
            }
            addEdge(edges, prevNode, cycle[0]);
        }

        return new Graph(edges, size);
    }

    private void addEdge(LinkedList<Edge> edges, int from, int to) {
        double calcWeight = calculateWeight();
        edges.add(edge(from, to, calcWeight));
        if (direction == NOT_DIRECTED) {
            edges.add(edge(to, from, calcWeight));
        }
    }

    private Config getConfig() {
        return new Config(
                direction == GeneratorOption.DIRECTED,
                weight == GeneratorOption.WEIGHTED,
                true);
    }

    private int minCycle() {
        if (size < 3) return 1;
        int maxEdges = calculateMaxEdges();
        if (density == SPARSE && maxEdges <= 3) return 1;
        return 3;
    }

    private int maxCycle() {
        int maxEdges = calculateMaxEdges();
        if (size == 1) return 1;
        if (size == 2) return direction == DIRECTED ? 2 : 1;
        if (density == SPARSE && maxEdges <= size) return maxEdges;
        return size;
    }

    private int calculateCycleSize() {
        return minCycle() == maxCycle() ? maxCycle() :
                random.nextInt(minCycle(), maxCycle());
    }


    private double calculateWeight() {
        return weight == NOT_WEIGHTED ? 1.0 : random.nextInt(1, 20);
    }

    private int calculateMaxEdges() {
        int temp = (density == SPARSE) ? (int)Math.floor((size * size) * 0.5) : size * size;
        return direction == DIRECTED ? temp : (int)Math.floor(temp / 2);
    }

    private double calculateDensity() {
        if (density == DENSE)  return random.nextDouble(0.6, 0.8);
        return random.nextDouble(0.1, 0.3);
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

    @Override
    public GeneratedGraph generate() {
        return new GeneratedGraph(getGraph(), getConfig());
    }

}
