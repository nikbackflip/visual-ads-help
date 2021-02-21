package com.backflip.vadsh.ds.graph.generator;

import com.backflip.vadsh.ds.graph.Config;
import com.backflip.vadsh.ds.graph.Graph;
import com.backflip.vadsh.ds.graph.analyzer.AnalyticDefinition;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
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
        Graph graph = generator.generate().getGraph();
        Config config = generator.generate().getConfig();

        //then
        assertEquals(size, graph.n());
        expectedAnalytics.forEach(analytic -> assertTrue(analytic.analyze(graph, config)));
        notExpectedAnalytics.forEach(analytic -> assertFalse(analytic.analyze(graph, config)));
    }

    private static Stream<Arguments> input() {
        return Stream.of(
                Stream.generate(
                        () -> Arguments.of(
                                ThreadLocalRandom.current().nextInt(1, 15), WEIGHTED, DIRECTED,
                                List.of(AnalyticDefinition.WEIGHTED, AnalyticDefinition.DIRECTIONAL, AnalyticDefinition.COMPLETE, AnalyticDefinition.DENSE),
                                List.of(AnalyticDefinition.SPARSE)))
                        .limit(100),
                Stream.generate(
                        () -> Arguments.of(
                                ThreadLocalRandom.current().nextInt(1, 15), NOT_WEIGHTED, NOT_DIRECTED,
                                List.of(AnalyticDefinition.COMPLETE, AnalyticDefinition.DENSE),
                                List.of(AnalyticDefinition.WEIGHTED, AnalyticDefinition.DIRECTIONAL, AnalyticDefinition.SPARSE)))
                        .limit(100)
        ).flatMap(s -> s);
    }
}
