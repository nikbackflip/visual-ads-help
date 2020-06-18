package com.backflip.vadsh.service.impl;

import com.backflip.vadsh.service.FileStorage;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

import static java.nio.charset.StandardCharsets.UTF_8;

@Component
public class LocalFileStorage implements FileStorage {

    @Value("${path}")
    private String pathBase;

    public void setPathBase(String pathBase) {
        this.pathBase = pathBase;
    }

    @Override
    @SneakyThrows
    public String saveToFile(String name, String content) {

        String path = pathBase + name;
        FileUtils.writeStringToFile(new File(path), content, UTF_8);

        return path;
    }
}
