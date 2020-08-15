package com.backflip.vadsh.controller.analyzer;

import com.backflip.vadsh.controller.dto.GraphRequest;
import com.backflip.vadsh.controller.dto.ResponseDto;
import com.backflip.vadsh.ds.graph.Edge;
import com.backflip.vadsh.ds.graph.Graph;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.backflip.vadsh.controller.analyzer.Analytic.*;
import static com.backflip.vadsh.ds.graph.GraphAnalyzer.*;
import static java.util.stream.Collectors.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Log4j2
@Controller
@RequestMapping("/analytics")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AnalyzerController {

    @ResponseBody
    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseDto standardAnalytics(@RequestBody GraphRequest graphRequest) {

        int n = graphRequest.getNodes().size();
        List<Edge> edges = graphRequest.getEdges().stream()
                .map(em -> new Edge(em.getFromId(), em.getToId(), em.getWeight()))
                .collect(toList());
        Graph graph = new Graph(edges, n);

        return AnalyticsResponse.builder()
                .withAnalytic(DIRECTIONAL.label(), graphRequest.getConfig().isGraphDirectional())
                .withAnalytic(WEIGHTED.label(), graphRequest.getConfig().isGraphWeighted())
                .withAnalytic(COMPLETE.label(), complete(graph, graphRequest.getConfig().isSelfLoopsAllowed()))
                .withAnalytic(TREE.label(), tree(graph, graphRequest.getConfig().isGraphDirectional()))
                .withAnalytic(DAG.label(), dag(graph, graphRequest.getConfig().isGraphDirectional()))
                .build();
    }

}
