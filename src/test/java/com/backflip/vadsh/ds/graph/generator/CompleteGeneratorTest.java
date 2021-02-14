package com.backflip.vadsh.ds.graph.generator;

import com.backflip.vadsh.ds.graph.Config;
import com.backflip.vadsh.ds.graph.Graph;
import com.backflip.vadsh.ds.graph.analyzer.AnalyticDefinition;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static com.backflip.vadsh.ds.graph.generator.GeneratorOption.*;
import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.*;

public class CompleteGeneratorTest {

    @ParameterizedTest
    @MethodSource("input")
    public void taskTest(int size, GeneratorOption weight, GeneratorOption direction,
                         List<AnalyticDefinition> expectedAnalytics, List<AnalyticDefinition> notExpectedAnalytics) {
        //given
        GraphGenerator generator = new CompleteGraphGenerator(size, weight, direction);

        //when
        Graph graph = generator.getGraph();
        Config config = generator.getConfig();

        //then
        assertEquals(size, graph.n());
        expectedAnalytics.forEach(analytic -> assertTrue(analytic.analyze(graph, config)));
        notExpectedAnalytics.forEach(analytic -> assertFalse(analytic.analyze(graph, config)));
    }

    private static Stream<Arguments> input() {
        return Stream.of(
                Arguments.of(
                        1, WEIGHTED, DIRECTED,
                        List.of(AnalyticDefinition.WEIGHTED, AnalyticDefinition.DIRECTIONAL, AnalyticDefinition.COMPLETE),
                        emptyList()),
                Arguments.of(
                        6, WEIGHTED, NOT_DIRECTED,
                        List.of(AnalyticDefinition.WEIGHTED, AnalyticDefinition.COMPLETE),
                        List.of(AnalyticDefinition.DIRECTIONAL)),
                Arguments.of(
                        7, NOT_WEIGHTED, DIRECTED,
                        List.of(AnalyticDefinition.DIRECTIONAL, AnalyticDefinition.COMPLETE),
                        List.of(AnalyticDefinition.WEIGHTED)),
                Arguments.of(
                        20, NOT_WEIGHTED, NOT_DIRECTED,
                        List.of(AnalyticDefinition.COMPLETE),
                        List.of(AnalyticDefinition.WEIGHTED, AnalyticDefinition.DIRECTIONAL))
                );
    }

}
