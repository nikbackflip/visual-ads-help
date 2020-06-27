package com.backflip.vadsh.templates;

import com.backflip.vadsh.service.FileStorage;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RegExUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

public abstract class Template<T extends TemplateArgs> {

    @SneakyThrows
    public String construct(T args) {

        InputStream file = new ClassPathResource(getSource(), this.getClass().getClassLoader()).getInputStream();
        String content = IOUtils.toString(file, UTF_8);

        for (Map.Entry<String, String> entry : args.getArgs().entrySet()) {
            String argName = entry.getKey();
            String replacement = entry.getValue();
            content = RegExUtils.replaceAll(content, "\\{" + argName + "}", replacement);
        }

        return getStorage().save(content);
    }

    public abstract String getSource();

    public abstract String getFinalName();

    public abstract FileStorage getStorage();

}
