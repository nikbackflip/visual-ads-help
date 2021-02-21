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

public class AcyclicGeneratorTest {

    @ParameterizedTest
    @MethodSource("input")
    public void taskTest(int size, GeneratorOption weight, GeneratorOption direction,
                         List<AnalyticDefinition> expectedAnalytics, List<AnalyticDefinition> notExpectedAnalytics) {
        //given
        GraphGenerator generator = new AcyclicGraphGenerator(size, weight, direction);

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
                        5, WEIGHTED, DIRECTED,
                        List.of(AnalyticDefinition.WEIGHTED, AnalyticDefinition.DIRECTIONAL, AnalyticDefinition.SPARSE),
                        List.of(AnalyticDefinition.DENSE, AnalyticDefinition.CYCLIC)),
                Arguments.of(
                        7, WEIGHTED, NOT_DIRECTED,
                        List.of(AnalyticDefinition.WEIGHTED, AnalyticDefinition.SPARSE),
                        List.of(AnalyticDefinition.DENSE, AnalyticDefinition.DIRECTIONAL, AnalyticDefinition.CYCLIC)),
                Arguments.of(
                        8, NOT_WEIGHTED, NOT_DIRECTED,
                        List.of(AnalyticDefinition.SPARSE),
                        List.of(AnalyticDefinition.WEIGHTED, AnalyticDefinition.DENSE, AnalyticDefinition.DIRECTIONAL, AnalyticDefinition.CYCLIC)),
                Arguments.of(
                        1, NOT_WEIGHTED, DIRECTED,
                        List.of(AnalyticDefinition.DIRECTIONAL/*, AnalyticDefinition.SPARSE*/),
                        List.of(AnalyticDefinition.WEIGHTED, AnalyticDefinition.CYCLIC)),
                Arguments.of(
                        2, WEIGHTED, DIRECTED,
                        List.of(AnalyticDefinition.WEIGHTED, AnalyticDefinition.DIRECTIONAL, AnalyticDefinition.SPARSE),
                        List.of(AnalyticDefinition.CYCLIC)),
                Arguments.of(
                        2, WEIGHTED, NOT_DIRECTED,
                        List.of(AnalyticDefinition.WEIGHTED, AnalyticDefinition.SPARSE),
                        List.of(AnalyticDefinition.DIRECTIONAL, AnalyticDefinition.CYCLIC))
        );
    }

}
