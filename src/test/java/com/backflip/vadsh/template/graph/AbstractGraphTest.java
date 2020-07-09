package com.backflip.vadsh.template.graph;

import com.backflip.vadsh.template.graph.proxy.IGraph;
import com.backflip.vadsh.templates.graph.GraphArgs;
import com.backflip.vadsh.templates.graph.GraphTemplate;
import org.joor.Reflect;


public abstract class AbstractGraphTest {

    static IGraph graphReflect;

    static void init(GraphArgs args) {
        String content = new GraphTemplate().construct(args);
        graphReflect = Reflect.compile("Graph", content).create().as(IGraph.class);
    }
}
