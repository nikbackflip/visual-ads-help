package com.backflip.vadsh.template.graph;

import com.backflip.vadsh.service.impl.LocalFileStorage;
import com.backflip.vadsh.template.graph.proxy.IGraph;
import com.backflip.vadsh.templates.graph.GraphArgs;
import com.backflip.vadsh.templates.graph.GraphTemplate;
import org.apache.commons.io.FileUtils;
import org.joor.Reflect;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import static java.nio.charset.StandardCharsets.UTF_8;

public abstract class AbstractGraphTest {

    @TempDir
    public static Path tempDirPath;

    private static LocalFileStorage storage = new LocalFileStorage();

    static IGraph graphReflect;

    static void init(GraphArgs args) throws IOException {
        storage.setPathBase(tempDirPath.toString());

        String path = new GraphTemplate(storage).construct(args);

        File file = new File(path);
        String content = FileUtils.readFileToString(file, UTF_8);
        graphReflect = Reflect.compile("Graph", content).create().as(IGraph.class);
    }
}
