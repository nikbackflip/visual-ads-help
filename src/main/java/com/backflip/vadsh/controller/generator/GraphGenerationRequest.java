package com.backflip.vadsh.controller.generator;

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
}

@Getter
@Setter
@AllArgsConstructor
class NodeModel {
    private int id;
    private String name;
}
