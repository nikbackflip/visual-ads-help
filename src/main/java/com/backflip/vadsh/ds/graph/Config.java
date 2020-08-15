package com.backflip.vadsh.ds.graph;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Config {

    private final boolean graphDirectional;
    private final boolean graphWeighted;
    private final boolean selfLoopsAllowed;

}
