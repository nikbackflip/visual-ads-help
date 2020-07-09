package com.backflip.vadsh.controller.generator;

import com.backflip.vadsh.templates.graph.GraphArgs;
import com.backflip.vadsh.templates.graph.GraphTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static java.util.Comparator.comparingInt;
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

        fixIds(graphRequest);
        GraphArgs.Builder graphModel = GraphArgs.builder();
        withEdges(graphModel, graphRequest.getEdges());
        withNodes(graphModel, graphRequest.getNodes());

        String content = template.construct(graphModel.build());

        log.debug("Graph generated");
        return new GraphGeneratorResponse(content);
    }

    private void fixIds(GraphGenerationRequest graphRequest) {
        List<NodeModel> nodes = graphRequest.getNodes();
        List<EdgeModel> edges = graphRequest.getEdges();

        nodes.sort(comparingInt(NodeModel::getId));
        int n = nodes.size();
        for (int i = 0; i < n; i++) {
            NodeModel node = nodes.get(i);
            int oldId = node.getId();
            for (EdgeModel edge: edges) {
                if (edge.getFromId() == oldId) edge.setFromId(i);
                if (edge.getToId() == oldId) edge.setToId(i);
            }
            node.setId(i);
        }
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
