package com.backflip.vadsh.ds.graph.layout;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class HexGridLayout implements Layout {

    protected final int gridX;
    protected final int gridY;
    private final int radius = 40; //hexagon outer radius
    private final double w = radius * Math.cos(Math.toRadians(30));
    private final double h = Math.sqrt(3 * w * w);

    protected HexGridLayout(int x, int y) {
        this.gridX = (int) Math.floor((x - w) / (2 * w)) - 1;
        this.gridY = (int) Math.floor((y - h) / h);
    }

    protected int distanceOnGrid(int from, int to) {
        int xi = from % gridX;
        int yi = from / gridX;
        int xj = to % gridX;
        int yj = to / gridX;

        int calcDist = Math.abs(xi - xj) + Math.abs(yi - yj);

        int availableShortcuts = 0;
        if (Math.abs(yi - yj) != 0) {
            boolean movementBack = xi - xj > 0;
            boolean evenStart = yi % 2 == 0;
            if ((movementBack && evenStart) || (!movementBack && !evenStart))
                availableShortcuts = (int) Math.ceil((float) Math.abs(yi - yj) / 2);
            if ((movementBack && !evenStart) || (!movementBack && evenStart))
                availableShortcuts = (int) Math.floor((float) Math.abs(yi - yj) / 2);
        }
        return calcDist - Math.min(availableShortcuts, Math.abs(xi - xj));
    }

    protected List<Integer> getNeighboursOnGris(int i) {

        int xi = i % gridX;
        int yi = i / gridX;

        List<Integer> neighbours = new LinkedList<>();

        if (xi != 0) neighbours.add(i - 1);
        if (xi != gridX - 1) neighbours.add(i + 1);

        boolean even = yi % 2 != 0;
        if (yi != 0) {
            if (!(xi == 0 && !even)) neighbours.add(even ? i - gridX : i - gridX - 1);
            if (!(xi == gridX - 1 && even)) neighbours.add(even ? i - gridX + 1 : i - gridX);
        }

        if (yi != gridY - 1) {
            if (!(xi == 0 && !even)) neighbours.add(even ? i + gridX : i + gridX - 1);
            if (!(xi == gridX - 1 && even)) neighbours.add(even ? i + gridX + 1 : i + gridX);
        }
        return neighbours;
    }

    protected Map<Integer, Coordinates> toCoordinates(int[] pos) {
        return IntStream.range(0, pos.length)
                .boxed()
                .collect(Collectors.toMap(
                        i -> i, i -> {
                            double margin = (pos[i] / gridX) % 2 == 0 ? 0 : w;
                            double cx = (pos[i] % gridX) * (2 * w) + margin + w + w;
                            double cy = (pos[i] / gridX) * h + h;
                            return new Coordinates((int)cx, (int)cy);
                        }
                ));
    }

}
