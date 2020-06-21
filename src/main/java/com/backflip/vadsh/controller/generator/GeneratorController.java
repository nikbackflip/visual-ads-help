package com.backflip.vadsh.controller.generator;

import com.backflip.vadsh.service.FileStorage;
import com.backflip.vadsh.templates.graph.GraphArgs;
import com.backflip.vadsh.templates.graph.GraphTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/generator")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class GeneratorController {

    private final GraphTemplate template;
    private final FileStorage storage;

    @PostMapping(path = "/graph")
    public void generateGraph(
            @RequestBody GraphGenerationRequest graphRequest,
            HttpServletResponse response) throws Exception {

        GraphArgs.Builder graphModel = GraphArgs.builder();
        withEdges(graphModel, graphRequest.getEdges());
        withNodes(graphModel, graphRequest.getNodes());

        String contentId = template.construct(graphModel.build());

        response.setContentType("application/plain");
        response.addHeader("Content-Disposition", "attachment; filename=" + template.getFinalName());
        response.getOutputStream().write(storage.getContent(contentId));
        response.getOutputStream().flush();
    }

    private void withEdges(GraphArgs.Builder builder, List<EdgeModel> list) {
        list.forEach(e -> {
            switch (e.getDirection()) {
                case NO_DIRECTIONS:
                case BOTH_DIRECTIONS:
                    builder.withEdge(e.getFromId(), e.getToId(), e.getWeight());
                    builder.withEdge(e.getToId(), e.getFromId(), e.getWeight());
                    break;
                case FORWARD_DIRECTION:
                    builder.withEdge(e.getFromId(), e.getToId(), e.getWeight());
                    break;
                case REVERSE_DIRECTION:
                    builder.withEdge(e.getToId(), e.getFromId(), e.getWeight());
                    break;
            }
        });
    }

    private void withNodes(GraphArgs.Builder builder, List<NodeModel> list) {
        list.forEach(n -> {
            builder.withNode(n.getId(), n.getName());
        });
    }

}
