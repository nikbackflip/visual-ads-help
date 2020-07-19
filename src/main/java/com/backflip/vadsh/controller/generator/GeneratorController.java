package com.backflip.vadsh.controller.generator;

import com.backflip.vadsh.templates.graph.GraphArgs;
import com.backflip.vadsh.templates.graph.GraphTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static org.springframework.http.MediaType.*;

@Log4j2
@Controller
@RequestMapping("/generator")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class GeneratorController {

    private final GraphTemplate template;

    @ResponseBody
    @PostMapping(path = "/graph", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public GraphGeneratorResponse generateGraph(
            @RequestBody GraphGenerationRequest graphRequest) {

        GraphArgs.Builder graphModel = GraphArgs.builder().ofSize(graphRequest.getNodes().size());
        withEdges(graphModel, graphRequest.getEdges());

        String content = template.construct(graphModel.build());

        log.debug("Graph generated");
        return new GraphGeneratorResponse(content);
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
                case SELF_DIRECTION:
                    builder.withEdge(e.getFromId(), e.getToId(), e.getWeight());
                    break;
                case REVERSE_DIRECTION:
                    builder.withEdge(e.getToId(), e.getFromId(), e.getWeight());
                    break;
            }
        });
    }

}
