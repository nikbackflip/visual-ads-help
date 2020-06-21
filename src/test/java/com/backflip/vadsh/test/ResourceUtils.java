package com.backflip.vadsh.test;

import lombok.SneakyThrows;
import org.springframework.core.io.ClassPathResource;

public final class ResourceUtils {

    @SneakyThrows
    public static byte[] fromFile(String path) {
        return new ClassPathResource(path).getInputStream().readAllBytes();
    }
}
