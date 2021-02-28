package com.backflip.vadsh.controller.generator;

import com.backflip.vadsh.ds.graph.generator.GeneratedGraph;
import com.backflip.vadsh.ds.graph.generator.GeneratorOption;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static com.backflip.vadsh.ds.graph.generator.GraphGenerator.getGenerator;
import static com.backflip.vadsh.ds.graph.layout.Layout.getLayout;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Log4j2
@Controller
@RequestMapping("/generator")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class GeneratorController {

    @ResponseBody
    @GetMapping(path = "options", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public OptionsResponse availableOptions(@RequestParam(name = "for", defaultValue = "") List<GeneratorOption> forOptions) {
        return OptionsResponse.builder().availableOptions(GeneratorOption.compatibleOptionsFor(forOptions)).build();
    }

    @ResponseBody
    @GetMapping(path = "generate", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public GraphResponse generateGraph(@RequestParam(name = "for") List<GeneratorOption> forOptions,
                                       @RequestParam(name = "x", required = false) Integer x, @RequestParam(name = "y", required = false) Integer y) {
        GeneratedGraph generatedGraph = getGenerator(ThreadLocalRandom.current().nextInt(3, 15), forOptions).generate();

        GraphResponse.GraphResponseBuilder response = GraphResponse.builder()
                .graph(generatedGraph.getGraph().adjacencyMatrix())
                .config(generatedGraph.getConfig());

        if (x != null && y != null) {
            response.coordinates(getLayout(generatedGraph.getGraph(), generatedGraph.getConfig(), x, y).layout());
        }

        return response.build();
    }

}
