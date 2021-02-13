package com.backflip.vadsh.ds.graph.generator;

import java.util.*;
import java.util.stream.Collectors;

public enum GeneratorOption {

    //                           DIRECTED,  NOT_DIRECTED,  WEIGHTED,  NOT_WEIGHTED,  COMPLETE,  SPARSE,  DENSE,  DAG,    CYCLIC,  ACYCLIC,
    DIRECTED(       false,     false,         true,      true,          true,      true,    true,   true,   true,    true),
    NOT_DIRECTED(   false,     false,         true,      true,          true,      true,    true,   false,  true,    true),
    WEIGHTED(       true,      true,          false,     false,         true,      true,    true,   true,   true,    true),
    NOT_WEIGHTED(   true,      true,          false,     false,         true,      true,    true,   true,   true,    true),
    COMPLETE(       true,      true,          true,      true,          false,     false,   false,  false,  true,    false),
    SPARSE(         true,      true,          true,      true,          false,     false,   false,  true,   true,    true),
    DENSE(          true,      true,          true,      true,          false,     false,   false,  true,   true,    true),
    DAG(            true,      false,         true,      true,          false,     true,    true,   false,  false,   true),
    CYCLIC(         true,      true,          true,      true,          true,      true,    true,   false,  false,   false),
    ACYCLIC(        true,      true,          true,      true,          false,     true,    true,   true,   false,   false);

    private final boolean[] compatibility;

    private final static int size;
    private final static boolean[][] optionsMatrix;
    private final static Map<GeneratorOption, Set<GeneratorOption>> optionsMap;

    public boolean compatibleWith(GeneratorOption o) {
        return compatible(this, o);
    }

    public boolean compatibleWith(Collection<GeneratorOption> c) {
        return compatible(this, c);
    }

    public static Set<GeneratorOption> allOptions() {
        return Set.of(values());
    }

    public Set<GeneratorOption> compatibleOptions() {
        return optionsMap.get(this);
    }

    public static Set<GeneratorOption> compatibleOptionsFor(Collection<GeneratorOption> forOptions) {
        if (forOptions.size() == 0) return allOptions();
        if (!compatible(forOptions)) throw new IllegalStateException("Incompatible options: " + Arrays.toString(forOptions.toArray()));

        return allOptions().stream()
                .filter(o -> !forOptions.contains(o))
                .filter(o -> compatible(o, forOptions))
                .collect(Collectors.toSet());
    }

    public static boolean compatible(Collection<GeneratorOption> options) {
        for (GeneratorOption o: options)
            if (!compatible(o, options)) return false;
        return true;
    }

    //================================================================================================================

    private static boolean compatible(GeneratorOption o1, GeneratorOption o2) {
        if (o1 == o2) return true;
        return optionsMatrix[o1.ordinal()][o2.ordinal()];
    }

    private static boolean compatible(GeneratorOption testOption, Collection<GeneratorOption> options) {
        for (GeneratorOption o: options)
            if (!compatible(testOption, o)) return false;
        return true;
    }

    //================================================================================================================

    GeneratorOption(boolean... compatibility) {
        this.compatibility = compatibility;
    }

    static {
        size = values().length;

        optionsMatrix = new boolean[values().length][];
        for (int i = 0; i < size; i++) {
            if (values()[i].compatibility.length != size) throw new IllegalStateException("Incorrect compatibility matrix");
            optionsMatrix[i] = Arrays.copyOf(values()[i].compatibility, values().length);
        }

        optionsMap = new EnumMap<>(GeneratorOption.class);
        for (int i = 0; i < size; i++) {
            Set<GeneratorOption> tempSet = EnumSet.noneOf(GeneratorOption.class);
            for (int j = 0; j < size; j++) {
                if (values()[i].compatibility[j] != values()[j].compatibility[i])
                    throw new IllegalStateException("Incorrect compatibility matrix");
                if (optionsMatrix[i][j]) tempSet.add(values()[j]);
            }
            optionsMap.put(values()[i], tempSet);
        }
    }
}
