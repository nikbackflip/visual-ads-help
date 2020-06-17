package com.backflip.vadsh.templates;

import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RegExUtils;

import java.io.File;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

public abstract class Template<T extends TemplateArgs> {

    @SneakyThrows
    public String construct(T args) {

        File file = new File(getClass().getResource(getSource()).getFile());
        String content = FileUtils.readFileToString(file, UTF_8);

        for (Map.Entry<String, String> entry : args.getArgs().entrySet()) {
            String argName = entry.getKey();
            String replacement = entry.getValue();
            content = RegExUtils.replaceAll(content, "\\{" + argName + "}", replacement);
        }


        String newPath = "C:\\Users\\Daria_Nikolaichenko\\projects\\VADSH\\visual-ads-help\\test\\" + getFinalName();
        FileUtils.writeStringToFile(new File(newPath), content, UTF_8);
        return newPath;
    }

    public abstract String getSource();

    public abstract String getFinalName();

}
