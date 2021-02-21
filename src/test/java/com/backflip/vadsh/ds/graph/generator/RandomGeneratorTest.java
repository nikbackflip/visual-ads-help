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
                Stream.generate(
                        () -> Arguments.of(
                                ThreadLocalRandom.current().nextInt(3, 15), WEIGHTED, DIRECTED, SPARSE, //FIXME from 1
                                List.of(AnalyticDefinition.DIRECTIONAL, AnalyticDefinition.SPARSE, AnalyticDefinition.WEIGHTED),
                                List.of(AnalyticDefinition.DENSE)))
                        .limit(100),
                Stream.generate(
                        () -> Arguments.of(
                                ThreadLocalRandom.current().nextInt(3, 15), NOT_WEIGHTED, NOT_DIRECTED, SPARSE, //FIXME from 1
                                List.of(AnalyticDefinition.SPARSE),
                                List.of(AnalyticDefinition.DIRECTIONAL, AnalyticDefinition.DENSE, AnalyticDefinition.WEIGHTED)))
                        .limit(100),
                Stream.generate(
                        () -> Arguments.of(
                                ThreadLocalRandom.current().nextInt(3, 15), NOT_WEIGHTED, DIRECTED, DENSE, //FIXME from 1
                                List.of(AnalyticDefinition.DIRECTIONAL, AnalyticDefinition.DENSE),
                                List.of(AnalyticDefinition.SPARSE, AnalyticDefinition.WEIGHTED)))
                        .limit(100),
                Stream.generate(
                        () -> Arguments.of(
                                ThreadLocalRandom.current().nextInt(3, 15), WEIGHTED, NOT_DIRECTED, DENSE, //FIXME from 1
                                List.of(AnalyticDefinition.DENSE, AnalyticDefinition.WEIGHTED),
                                List.of(AnalyticDefinition.DIRECTIONAL, AnalyticDefinition.SPARSE)))
                        .limit(100)
        ).flatMap(s -> s);
    }

}
