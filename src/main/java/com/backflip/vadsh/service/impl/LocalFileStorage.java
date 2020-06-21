package com.backflip.vadsh.service.impl;

import com.backflip.vadsh.service.FileStorage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.UUID;

import static java.nio.charset.StandardCharsets.UTF_8;

@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class LocalFileStorage implements FileStorage {

    @Value("${storage.local.path}")
    private String pathBase;

    public void setPathBase(String pathBase) {
        this.pathBase = pathBase;
    }

    @Override
    @SneakyThrows
    public String save(String content) {

        String fileName = UUID.randomUUID().toString();
        String path = pathBase + fileName;

        FileUtils.writeStringToFile(new File(path), content, UTF_8);

        return fileName;
    }

    @Override
    @SneakyThrows
    public byte[] getContent(String contentId) {
        return FileUtils.readFileToByteArray(new File(pathBase + contentId));
    }

    @Override
    @SneakyThrows
    public void clearStorage() {
        FileUtils.cleanDirectory(new File(pathBase));
    }
}
