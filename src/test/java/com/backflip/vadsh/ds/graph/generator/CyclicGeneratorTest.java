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
import static org.junit.jupiter.api.Assertions.*;

public class CyclicGeneratorTest {

    @ParameterizedTest
    @MethodSource("input")
    public void taskTest(int size, GeneratorOption weight, GeneratorOption direction, GeneratorOption density,
                         List<AnalyticDefinition> expectedAnalytics, List<AnalyticDefinition> notExpectedAnalytics) {
        //given
        GraphGenerator generator = new CyclicGraphGenerator(size, weight, direction, density);

        //when
        Graph graph = generator.generate().getGraph();
        Config config = generator.generate().getConfig();

        //then
        assertEquals(size, graph.n());
        expectedAnalytics.forEach(analytic -> assertTrue(analytic.analyze(graph, config)));
        notExpectedAnalytics.forEach(analytic -> assertFalse(analytic.analyze(graph, config)));
    }

    private static Stream<Arguments> input() {
        return Stream.of(
                Arguments.of(
                        1, NOT_WEIGHTED, DIRECTED, DENSE,
                        List.of(AnalyticDefinition.DIRECTIONAL, AnalyticDefinition.CYCLIC),
                        List.of(AnalyticDefinition.WEIGHTED)),
                Arguments.of(
                        2, NOT_WEIGHTED, DIRECTED, DENSE,
                        List.of(AnalyticDefinition.DIRECTIONAL, AnalyticDefinition.CYCLIC),
                        List.of(AnalyticDefinition.WEIGHTED)),
                Arguments.of(
                        3, NOT_WEIGHTED, DIRECTED, DENSE,
                        List.of(AnalyticDefinition.DIRECTIONAL, AnalyticDefinition.CYCLIC),
                        List.of(AnalyticDefinition.WEIGHTED)),
                Arguments.of(
                        5, NOT_WEIGHTED, DIRECTED, DENSE,
                        List.of(AnalyticDefinition.DIRECTIONAL, AnalyticDefinition.CYCLIC),
                        List.of(AnalyticDefinition.WEIGHTED)),
                Arguments.of(
                        10, NOT_WEIGHTED, DIRECTED, DENSE,
                        List.of(AnalyticDefinition.DIRECTIONAL, AnalyticDefinition.CYCLIC),
                        List.of(AnalyticDefinition.WEIGHTED)),
                Arguments.of(
                        15, NOT_WEIGHTED, DIRECTED, DENSE,
                        List.of(AnalyticDefinition.DIRECTIONAL, AnalyticDefinition.CYCLIC),
                        List.of(AnalyticDefinition.WEIGHTED)),
                Arguments.of(
                        1, NOT_WEIGHTED, NOT_DIRECTED, DENSE,
                        List.of(AnalyticDefinition.CYCLIC),
                        List.of(AnalyticDefinition.WEIGHTED, AnalyticDefinition.DIRECTIONAL)),
                Arguments.of(
                        2, NOT_WEIGHTED, NOT_DIRECTED, DENSE,
                        List.of(AnalyticDefinition.CYCLIC),
                        List.of(AnalyticDefinition.WEIGHTED, AnalyticDefinition.DIRECTIONAL)),
                Arguments.of(
                        3, NOT_WEIGHTED, NOT_DIRECTED, DENSE,
                        List.of(AnalyticDefinition.CYCLIC),
                        List.of(AnalyticDefinition.WEIGHTED, AnalyticDefinition.DIRECTIONAL)),
                Arguments.of(
                        5, NOT_WEIGHTED, NOT_DIRECTED, DENSE,
                        List.of(AnalyticDefinition.CYCLIC),
                        List.of(AnalyticDefinition.WEIGHTED, AnalyticDefinition.DIRECTIONAL)),
                Arguments.of(
                        10, NOT_WEIGHTED, NOT_DIRECTED, DENSE,
                        List.of(AnalyticDefinition.CYCLIC),
                        List.of(AnalyticDefinition.WEIGHTED, AnalyticDefinition.DIRECTIONAL)),
                Arguments.of(
                        15, NOT_WEIGHTED, NOT_DIRECTED, DENSE,
                        List.of(AnalyticDefinition.CYCLIC),
                        List.of(AnalyticDefinition.WEIGHTED, AnalyticDefinition.DIRECTIONAL))
        );
    }

}
