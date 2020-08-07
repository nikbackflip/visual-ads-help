package com.backflip.vadsh.template.graph;

import com.backflip.vadsh.template.graph.proxy.IGraph;
import org.joor.Reflect;


public abstract class AbstractGraphTest {

    static IGraph graphReflect;

    static void init(GraphArgs args) {
        String content = new GraphTemplate().construct(args);
        graphReflect = Reflect.compile("Graph", content).create().as(IGraph.class);
    }
}
