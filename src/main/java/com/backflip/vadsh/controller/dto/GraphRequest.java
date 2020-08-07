package com.backflip.vadsh.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GraphRequest {
    private List<EdgeModel> edges;
    private List<NodeModel> nodes;

    @Getter
    @Setter
    @AllArgsConstructor
    public static class EdgeModel {
        private int fromId;
        private int toId;
        private double weight;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class NodeModel {
        private int id;
        private String name;
    }


}