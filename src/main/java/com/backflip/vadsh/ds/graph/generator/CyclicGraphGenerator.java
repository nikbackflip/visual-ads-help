package com.backflip.vadsh.ds.graph.generator;

import com.backflip.vadsh.ds.graph.Config;
import com.backflip.vadsh.ds.graph.Graph;

public class CyclicGraphGenerator implements GraphGenerator {

    private final int size;
    private final GeneratorOption weight;
    private final GeneratorOption direction;
    private final GeneratorOption density;

    protected CyclicGraphGenerator(int size, GeneratorOption weight, GeneratorOption direction, GeneratorOption density) {
        this.size = size;
        this.weight = weight;
        this.direction = direction;
        this.density = density;
    }

    @Override
    public Graph getGraph() {
        return null;
    }

    @Override
    public Config getConfig() {
        return null;
    }

}
