package com.backflip.vadsh.ds.graph.layout;

import com.backflip.vadsh.ds.graph.Config;
import com.backflip.vadsh.ds.graph.Graph;

import java.util.Map;
import java.util.stream.IntStream;

import static java.lang.Math.sin;
import static java.util.stream.Collectors.toMap;
import static org.apache.commons.lang3.math.NumberUtils.min;

public class CompleteLayout implements Layout {

    private final Graph graph;
    private final Config config;
    private final int x;
    private final int y;

    private final int margin = 40; //frontend stage margin
    private final int r = 20; //frontend node radius

    public CompleteLayout(Graph graph, Config config, int x, int y) {
        this.graph = graph;
        this.config = config;
        this.x = x;
        this.y = y;
    }

    @Override
    public Map<Integer, Coordinates> layout() {
        int n = graph.n();

        int maxRadius = min(x - margin * 2, y - margin * 2) / 2;
        int requiredRadius = n * 2 * r;
        int R = min(maxRadius, requiredRadius);

        int ox = x / 2;
        int oy = y / 2;

        double a = 2 * Math.PI / n;

        return IntStream.range(0, n)
                .boxed()
                .collect(toMap(
                        i -> i,
                        i -> new Coordinates(ox + R * Math.cos(i * a), oy + R * sin(i * a))));
    }
}
