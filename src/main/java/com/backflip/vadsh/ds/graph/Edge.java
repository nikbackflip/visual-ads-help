package com.backflip.vadsh.ds.graph;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode
@AllArgsConstructor
@Getter
@Setter
public final class Edge {

    private final int from;
    private final int to;
    private final double weight;

    public int from() {
        return from;
    }

    public int to() {
        return to;
    }

    public double weight() {
        return weight;
    }
}
