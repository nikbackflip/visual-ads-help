package com.backflip.vadsh.templates.graph;

import com.backflip.vadsh.templates.Template;


public class GraphTemplate extends Template<GraphArgs> {

    public String getSource() {
        return "/sources/Graph.java.template";
    }

    public String getFinalName() {
        return "Graph.java";
    }
}
