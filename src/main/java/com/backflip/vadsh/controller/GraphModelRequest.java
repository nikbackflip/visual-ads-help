package com.backflip.vadsh.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GraphModelRequest {

    private List<EdgeModel> edges;
    private List<NodeModel> nodes;

    @Getter
    @Setter
    @AllArgsConstructor
    static class EdgeModel {
        private int from;
        private int to;
        private double weight;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    static class NodeModel {
        private int id;
        private String name;
    }

}
