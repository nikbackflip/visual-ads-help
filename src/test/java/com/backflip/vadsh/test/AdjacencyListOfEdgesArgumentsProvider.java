package com.backflip.vadsh.test;

import com.backflip.vadsh.ds.graph.Edge;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.List;
import java.util.stream.Stream;

public class AdjacencyListOfEdgesArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(
                Arguments.of(
                        List.of(
                                new Edge(0, 1, 1),
                                new Edge(0, 2, 1),
                                new Edge(0, 3, 1),
                                new Edge(1, 0, 1),
                                new Edge(1, 2, 1),
                                new Edge(1, 3, 1),
                                new Edge(2, 0, 1),
                                new Edge(2, 1, 1),
                                new Edge(2, 3, 1),
                                new Edge(3, 0, 1),
                                new Edge(3, 1, 1),
                                new Edge(3, 2, 1)),
                        4,
                        List.of(
                                List.of(
                                        new Edge(0, 1, 1),
                                        new Edge(0, 2, 1),
                                        new Edge(0, 3, 1)),
                                List.of(
                                        new Edge(1, 0, 1),
                                        new Edge(1, 2, 1),
                                        new Edge(1, 3, 1)),
                                List.of(
                                        new Edge(2, 0, 1),
                                        new Edge(2, 1, 1),
                                        new Edge(2, 3, 1)),
                                List.of(
                                        new Edge(3, 0, 1),
                                        new Edge(3, 1, 1),
                                        new Edge(3, 2, 1)))),

                Arguments.of(
                        List.of(),
                        0,
                        List.of()),

                Arguments.of(
                        List.of(
                                new Edge(0, 2, 1),
                                new Edge(2, 0, 1)),
                        4,
                        List.of(
                                List.of(
                                        new Edge(0, 2, 1)),
                                List.of(),
                                List.of(
                                        new Edge(2, 0, 1)),
                                List.of())),

                Arguments.of(
                        List.of(),
                        4,
                        List.of(
                                List.of(),
                                List.of(),
                                List.of(),
                                List.of())),

                Arguments.of(
                        List.of(
                                new Edge(0, 2, 1),
                                new Edge(2, 1, -1),
                                new Edge(1, 3, 10),
                                new Edge(3, 0, 11)),
                        4,
                        List.of(
                                List.of(
                                        new Edge(0, 2, 1)),
                                List.of(
                                        new Edge(1, 3, 10)),
                                List.of(
                                        new Edge(2, 1, -1)),
                                List.of(
                                        new Edge(3, 0, 11))))

        );
    }

}
