package com.backflip.vadsh.ds.graph.layout;

import com.backflip.vadsh.ds.graph.generator.GeneratedGraph;
import com.backflip.vadsh.ds.graph.generator.GeneratorOption;
import com.backflip.vadsh.ds.graph.generator.GraphGenerator;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LayoutTest {

    @Test
    public void layoutRandomGraph() {
        //given
        GeneratedGraph generatedGraph = GraphGenerator.getGenerator(10, List.of(GeneratorOption.RANDOM, GeneratorOption.NOT_DIRECTED, GeneratorOption.DENSE)).generate();

        //when
        Layout layout = Layout.getLayout(generatedGraph.getGraph(), generatedGraph.getConfig(), 1000, 1000);

        //then
        assertTrue(layout instanceof RandomLayout);
        assertNotNull(layout.layout());
    }

    @Test
    public void layoutCompleteGraph() {
        //given
        GeneratedGraph generatedGraph = GraphGenerator.getGenerator(10, List.of(GeneratorOption.COMPLETE)).generate();

        //when
        Layout layout = Layout.getLayout(generatedGraph.getGraph(), generatedGraph.getConfig(), 1000, 1000);

        //then
        assertTrue(layout instanceof CompleteLayout);
        assertNotNull(layout.layout());
    }

    @Test
    public void layoutDagGraph() {
        //given
        GeneratedGraph generatedGraph = GraphGenerator.getGenerator(10, List.of(GeneratorOption.DAG)).generate();

        //when
        Layout layout = Layout.getLayout(generatedGraph.getGraph(), generatedGraph.getConfig(), 1000, 1000);

        //then
        assertTrue(layout instanceof DagLayout);
        assertNotNull(layout.layout());
    }
}
