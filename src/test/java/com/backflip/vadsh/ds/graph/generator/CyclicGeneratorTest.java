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
                Stream.generate(
                        () -> Arguments.of(
                                ThreadLocalRandom.current().nextInt(2, 15), NOT_WEIGHTED, DIRECTED, SPARSE,
                                List.of(AnalyticDefinition.DIRECTIONAL, AnalyticDefinition.CYCLIC, AnalyticDefinition.SPARSE),
                                List.of(AnalyticDefinition.WEIGHTED, AnalyticDefinition.DENSE)))
                        .limit(100),
                Stream.generate(
                        () -> Arguments.of(
                                ThreadLocalRandom.current().nextInt(3, 15), NOT_WEIGHTED, NOT_DIRECTED, SPARSE,
                                List.of(AnalyticDefinition.CYCLIC, AnalyticDefinition.SPARSE),
                                List.of(AnalyticDefinition.DIRECTIONAL, AnalyticDefinition.WEIGHTED, AnalyticDefinition.DENSE)))
                        .limit(100),
                Stream.generate(
                        () -> Arguments.of(
                                ThreadLocalRandom.current().nextInt(1, 15), NOT_WEIGHTED, DIRECTED, DENSE,
                                List.of(AnalyticDefinition.DIRECTIONAL, AnalyticDefinition.CYCLIC, AnalyticDefinition.DENSE),
                                List.of(AnalyticDefinition.WEIGHTED, AnalyticDefinition.SPARSE)))
                        .limit(100),
                Stream.generate(
                        () -> Arguments.of(
                                ThreadLocalRandom.current().nextInt(4, 15), NOT_WEIGHTED, NOT_DIRECTED, DENSE, //TODO CHANGE TO 1
                                List.of(AnalyticDefinition.CYCLIC, AnalyticDefinition.DENSE),
                                List.of(AnalyticDefinition.DIRECTIONAL, AnalyticDefinition.WEIGHTED, AnalyticDefinition.SPARSE)))
                        .limit(100)
        ).flatMap(s -> s);
    }

}
