package com.backflip.vadsh.templates;

import java.util.Map;

import java.io.*;

import lombok.SneakyThrows;
import org.apache.commons.io.*;
import org.apache.commons.lang3.RegExUtils;

import static java.nio.charset.StandardCharsets.UTF_8;

public enum Templates {

    GRAPH("/sources/Graph.java.template");

    private final String path;

    Templates(String path) {
        this.path = path;
    }

    @SneakyThrows
    public String construct(Map<String, String> args) {
        File file = new File(getClass().getResource(path).getFile());
        String content = FileUtils.readFileToString(file, UTF_8);

        for (Map.Entry<String, String> entry : args.entrySet()) {
            String argName = entry.getKey();
            String replacement = entry.getValue();
            content = RegExUtils.replaceAll(content, "\\{" + argName + "}", replacement);
        }


        String newPath = "C:\\Users\\Daria_Nikolaichenko\\projects\\VADSH\\visual-ads-help\\test\\"
                + "Graph.java";
        FileUtils.writeStringToFile(new File(newPath), content, UTF_8);
        return newPath;
    }

}
