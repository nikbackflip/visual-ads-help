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

public class RandomGeneratorTest {

    @ParameterizedTest
    @MethodSource("input")
    public void taskTest(int size, GeneratorOption weight, GeneratorOption direction, GeneratorOption density,
                         List<AnalyticDefinition> expectedAnalytics, List<AnalyticDefinition> notExpectedAnalytics) {
        //given
        GraphGenerator generator = new RandomGraphGenerator(size, weight, direction, density);

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
                        5, WEIGHTED, DIRECTED, DENSE,
                        List.of(AnalyticDefinition.WEIGHTED, AnalyticDefinition.DIRECTIONAL, AnalyticDefinition.DENSE),
                        List.of(AnalyticDefinition.SPARSE, AnalyticDefinition.COMPLETE)),
                Arguments.of(
                        6, WEIGHTED, DIRECTED, SPARSE,
                        List.of(AnalyticDefinition.WEIGHTED, AnalyticDefinition.DIRECTIONAL, AnalyticDefinition.SPARSE),
                        List.of(AnalyticDefinition.DENSE, AnalyticDefinition.COMPLETE)),
                Arguments.of(
                        7, WEIGHTED, NOT_DIRECTED, SPARSE,
                        List.of(AnalyticDefinition.WEIGHTED, AnalyticDefinition.SPARSE),
                        List.of(AnalyticDefinition.DENSE, AnalyticDefinition.DIRECTIONAL, AnalyticDefinition.COMPLETE)),
                Arguments.of(
                        8, NOT_WEIGHTED, NOT_DIRECTED, DENSE,
                        List.of(AnalyticDefinition.DENSE),
                        List.of(AnalyticDefinition.WEIGHTED, AnalyticDefinition.SPARSE, AnalyticDefinition.DIRECTIONAL, AnalyticDefinition.COMPLETE)),
                Arguments.of(
                        1, WEIGHTED, DIRECTED, DENSE,
                        List.of(AnalyticDefinition.WEIGHTED, AnalyticDefinition.DIRECTIONAL, AnalyticDefinition.COMPLETE, AnalyticDefinition.DENSE),
                        List.of(AnalyticDefinition.SPARSE)),
                Arguments.of(
                        2, WEIGHTED, DIRECTED, DENSE,
                        List.of(AnalyticDefinition.WEIGHTED, AnalyticDefinition.DIRECTIONAL, AnalyticDefinition.DENSE),
                        List.of(AnalyticDefinition.SPARSE)),
                Arguments.of(
                        2, WEIGHTED, DIRECTED, SPARSE,
                        List.of(AnalyticDefinition.WEIGHTED, AnalyticDefinition.DIRECTIONAL/*, AnalyticDefinition.SPARSE*/),
                        List.of(AnalyticDefinition.DENSE, AnalyticDefinition.COMPLETE))
        );
    }

}
