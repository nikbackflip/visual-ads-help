package com.backflip.vadsh.ds.graph.layout;

import lombok.*;

@Getter
@AllArgsConstructor
public class Coordinates {

    public Coordinates(double x, double y) {
        this.x = (int)x;
        this.y = (int)y;
    }

    int x;
    int y;

}
