package com.backflip.vadsh.controller;

import com.backflip.vadsh.templates.graph.GraphArgs;
import com.backflip.vadsh.templates.graph.GraphTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class GeneratorController {

    private final GraphTemplate template;

    @PostMapping(path = "/download")
    public void generateGraphSource(
            @RequestBody GraphModelRequest graphModel,
            HttpServletResponse response) throws Exception {

        GraphArgs.Builder builder = GraphArgs.builder();
        graphModel.getEdges().forEach(e -> {
            builder.withEdge(e.getFrom(), e.getTo(), e.getWeight());
        });
        graphModel.getNodes().forEach(n -> {
            builder.withNode(n.getId(), n.getName());
        });

        String path = template.construct(builder.build());

        Path file = Paths.get(path);

        response.setContentType("application/plain");
        response.addHeader("Content-Disposition", "attachment; filename=" + template.getFinalName());
        Files.copy(file, response.getOutputStream());
        response.getOutputStream().flush();
    }

}
