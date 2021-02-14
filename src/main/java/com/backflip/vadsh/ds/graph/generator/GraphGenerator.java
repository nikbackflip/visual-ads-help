package com.backflip.vadsh.ds.graph.generator;

import com.backflip.vadsh.ds.graph.Config;
import com.backflip.vadsh.ds.graph.Graph;

import java.util.Arrays;
import java.util.Collection;

import static com.backflip.vadsh.ds.graph.generator.GeneratorOption.*;

public interface GraphGenerator {

    static GraphGenerator getGenerator(int size, Collection<GeneratorOption> options) {
        if (!GeneratorOption.compatible(options)) throw new IllegalStateException("Incompatible options: " + Arrays.toString(options.toArray()));
        if (size < 0) throw new IllegalStateException("Illegal graph size");

        return switch (typeDescriptor(options)) {
            case COMPLETE -> new CompleteGraphGenerator(size, weighedDescriptor(options), directionDescriptor(options));
            case CYCLIC -> new CyclicGraphGenerator(size, weighedDescriptor(options), directionDescriptor(options), densityDescriptor(options));
            case ACYCLIC -> new AcyclicGraphGenerator(size, weighedDescriptor(options), directionDescriptor(options), densityDescriptor(options));
            case DAG -> new DagGraphGenerator(size, weighedDescriptor(options), directionDescriptor(options), densityDescriptor(options));
            default -> new RandomGraphGenerator(size, weighedDescriptor(options), directionDescriptor(options), densityDescriptor(options));
        };
    }

    Graph getGraph();
    Config getConfig();

    private static GeneratorOption directionDescriptor(Collection<GeneratorOption> c) {
        if (c.contains(NOT_DIRECTED)) return NOT_DIRECTED;
        return DIRECTED;
    }

    private static GeneratorOption weighedDescriptor(Collection<GeneratorOption> c) {
        if (c.contains(NOT_WEIGHTED)) return NOT_WEIGHTED;
        return WEIGHTED;
    }

    private static GeneratorOption densityDescriptor(Collection<GeneratorOption> c) {
        if (c.contains(DENSE)) return DENSE;
        return SPARSE;
    }

    private static GeneratorOption typeDescriptor(Collection<GeneratorOption> c) {
        if (c.contains(COMPLETE)) return COMPLETE;
        if (c.contains(DAG)) return DAG;
        if (c.contains(CYCLIC)) return CYCLIC;
        if (c.contains(ACYCLIC)) return ACYCLIC;
        return RANDOM;
    }
}
