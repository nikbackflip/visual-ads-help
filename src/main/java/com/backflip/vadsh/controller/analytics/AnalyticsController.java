package com.backflip.vadsh.controller.analytics;

import com.backflip.vadsh.controller.dto.GraphRequest;
import com.backflip.vadsh.controller.dto.ResponseDto;
import com.backflip.vadsh.ds.graph.Config;
import com.backflip.vadsh.ds.graph.Edge;
import com.backflip.vadsh.ds.graph.Graph;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

import static com.backflip.vadsh.ds.graph.analyzer.AnalyticDefinition.*;
import static java.util.stream.Collectors.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Log4j2
@Controller
@RequestMapping("/analytics")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AnalyticsController {

    @ResponseBody
    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseDto standardAnalytics(@RequestBody GraphRequest graphRequest) {

        List<Edge> edges = graphRequest.getEdges().stream()
                .map(em -> new Edge(em.getFromId(), em.getToId(), em.getWeight()))
                .collect(toList());
        Graph graph = new Graph(edges, graphRequest.getNodes().size());
        Config config = new Config(
                graphRequest.getConfig().isGraphDirectional(),
                graphRequest.getConfig().isGraphWeighted(),
                graphRequest.getConfig().isSelfLoopsAllowed());

        AnalyticsResponse.Builder responseBuilder = AnalyticsResponse.builder();
        Arrays.stream(values()).forEach((a) -> {
            responseBuilder.withAnalytic(a.label(), a.analyze(graph, config));
        });

        return responseBuilder.build();
    }

}
