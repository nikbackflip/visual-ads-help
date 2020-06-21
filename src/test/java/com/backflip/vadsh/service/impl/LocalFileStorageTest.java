package com.backflip.vadsh.service.impl;


import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.nio.file.Path;


import static java.nio.charset.StandardCharsets.UTF_8;
import static org.apache.commons.io.FileUtils.readFileToString;
import static org.apache.commons.io.FileUtils.sizeOfDirectory;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class LocalFileStorageTest {

    @Autowired
    private LocalFileStorage storage;

    @TempDir
    public static Path tempDir;

    @BeforeEach
    public void setUp() {
        storage.setPathBase(tempDir.toString() + File.separator);
        storage.clearStorage();
    }

    @Test
    public void saveContent() throws Exception {
        //given
        String content = "content";

        //when
        String fileName = storage.save(content);

        //then
        assertEquals(
                content,
                readFileToString(new File(tempDir.toString() + File.separator + fileName), UTF_8));
    }

    @Test
    public void getContent() throws Exception {
        //given
        String content = "content";
        String fileName = "name";
        FileUtils.writeStringToFile(new File(tempDir.toString() + File.separator + fileName), content, UTF_8);

        //when
        String result = new String(storage.getContent(fileName));

        //then
        assertEquals(content, result);
    }

    @Test
    public void saveAndWriteContent() {
        //given
        String content = "content";

        //when
        String result = new String(storage.getContent(storage.save(content)));

        //then
        assertEquals(content, result);
    }

    @Test
    public void clearStorage() {
        //given
        String content = "content";
        storage.save(content);
        storage.save(content);

        //when
        storage.clearStorage();

        //then
        assertEquals(0, sizeOfDirectory(new File(tempDir.toString())));
    }

}