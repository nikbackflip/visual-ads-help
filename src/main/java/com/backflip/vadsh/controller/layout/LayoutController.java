package com.backflip.vadsh.controller.layout;

import com.backflip.vadsh.controller.dto.GraphRequest;
import com.backflip.vadsh.ds.graph.Config;
import com.backflip.vadsh.ds.graph.Edge;
import com.backflip.vadsh.ds.graph.Graph;
import com.backflip.vadsh.ds.graph.layout.Layout;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Log4j2
@Controller
@RequestMapping("/layout")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class LayoutController {

    @ResponseBody
    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public LayoutResponse layout(@RequestBody GraphRequest graphRequest,
                                 @RequestParam(name = "x") Integer x, @RequestParam(name = "y") Integer y) {
        List<Edge> edges = graphRequest.getEdges().stream()
                .map(em -> new Edge(em.getFromId(), em.getToId(), em.getWeight()))
                .collect(toList());
        Graph graph = new Graph(edges, graphRequest.getNodes().size());
        Config config = new Config(
                graphRequest.getConfig().isGraphDirectional(),
                graphRequest.getConfig().isGraphWeighted(),
                graphRequest.getConfig().isSelfLoopsAllowed());

        return LayoutResponse.builder()
                .coordinates(Layout.getLayout(graph, config).layout(x, y))
                .build();
    }
}
