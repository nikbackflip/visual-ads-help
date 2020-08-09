package com.backflip.vadsh.controller.analyzer;

import com.backflip.vadsh.controller.dto.GraphRequest;
import com.backflip.vadsh.controller.dto.ResponseDto;
import com.backflip.vadsh.ds.graph.Edge;
import com.backflip.vadsh.ds.graph.Graph;
import com.backflip.vadsh.ds.graph.GraphAnalyzer;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Log4j2
@Controller
@RequestMapping("/analyzer")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AnalyzerController {

    @ResponseBody
    @PostMapping(path = "/complete", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseDto graphComplete(@RequestBody GraphRequest graphRequest) {

        int n = graphRequest.getNodes().size();
        List<Edge> edges = graphRequest.getEdges().stream()
                .map(em -> new Edge(em.getFromId(), em.getToId(), em.getWeight()))
                .collect(toList());

        return AnalyzerResponse.builder()
                .checked(GraphAnalyzer.complete(new Graph(edges, n), graphRequest.getConfig().isSelfEdgesAllowed()))
                .build();
    }

    @ResponseBody
    @PostMapping(path = "/tree", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseDto graphIsTree(@RequestBody GraphRequest graphRequest) {

        int n = graphRequest.getNodes().size();
        List<Edge> edges = graphRequest.getEdges().stream()
                .map(em -> new Edge(em.getFromId(), em.getToId(), em.getWeight()))
                .collect(toList());

        return AnalyzerResponse.builder()
                .checked(GraphAnalyzer.tree(new Graph(edges, n), graphRequest.getConfig().isGraphDirectional()))
                .build();
    }

}
