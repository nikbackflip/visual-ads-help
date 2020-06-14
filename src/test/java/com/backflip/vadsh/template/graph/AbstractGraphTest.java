package com.backflip.vadsh.template.graph;

import com.backflip.vadsh.template.graph.proxy.IGraph;
import com.backflip.vadsh.templates.Templates;
import org.apache.commons.io.FileUtils;
import org.joor.Reflect;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

public abstract class AbstractGraphTest {

    static IGraph graphReflect;

    static void init(Map<String, String> args) throws IOException {
        String path = Templates.GRAPH.construct(args);
        File file = new File(path);
        String content = FileUtils.readFileToString(file, UTF_8);
        graphReflect = Reflect.compile("Graph", content).create().as(IGraph.class);
    }
}
