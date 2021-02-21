package com.backflip.vadsh.controller.generator;

import com.backflip.vadsh.ds.graph.generator.GeneratorOption;
import com.backflip.vadsh.ds.graph.generator.GraphGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

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
    public GraphResponse generateGraph(@RequestParam(name = "for") List<GeneratorOption> forOptions) {
        GraphGenerator generator = GraphGenerator.getGenerator(ThreadLocalRandom.current().nextInt(3, 15), forOptions);
        return GraphResponse.builder()
                .graph(generator.generate().getGraph().adjacencyMatrix())
                .config(generator.generate().getConfig())
                .build();
    }

}
