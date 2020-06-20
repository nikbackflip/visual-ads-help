package com.backflip.vadsh.controller.generator;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
class GraphGenerationRequest {

    private List<EdgeModel> edges;
    private List<NodeModel> nodes;

}

@Getter
@Setter
@AllArgsConstructor
class EdgeModel {
    private int fromId;
    private int toId;
    private double weight;
    private Direction direction;
}

@Getter
@Setter
@AllArgsConstructor
class NodeModel {
    private int id;
    private String name;
}

enum Direction {
    @JsonProperty("noDirections")
    NO_DIRECTIONS,
    @JsonProperty("bothDirections")
    BOTH_DIRECTIONS,
    @JsonProperty("forwardDirection")
    FORWARD_DIRECTION,
    @JsonProperty("reverseDirection")
    REVERSE_DIRECTION;
}
