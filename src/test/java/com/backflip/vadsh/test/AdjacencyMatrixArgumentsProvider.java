package com.backflip.vadsh.test;

import com.backflip.vadsh.ds.graph.Edge;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.List;
import java.util.stream.Stream;

public class AdjacencyMatrixArgumentsProvider implements ArgumentsProvider {

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
                        new double[][]{
                                {0,1,1,1},
                                {1,0,1,1},
                                {1,1,0,1},
                                {1,1,1,0},
                }),

                Arguments.of(
                        List.of(),
                        0,
                        new double[][]{}
                ),

                Arguments.of(
                        List.of(
                                new Edge(0, 2, 1),
                                new Edge(2, 0, 1)),
                        4,
                        new double[][]{
                                {0,0,1,0},
                                {0,0,0,0},
                                {1,0,0,0},
                                {0,0,0,0},
                }),

                Arguments.of(
                        List.of(),
                        4,
                        new double[][]{
                                {0,0,0,0},
                                {0,0,0,0},
                                {0,0,0,0},
                                {0,0,0,0},
                }),

                Arguments.of(
                        List.of(
                                new Edge(0, 2, 1),
                                new Edge(2, 1, -1),
                                new Edge(1, 3, 10),
                                new Edge(3, 0, 11)),
                        4,
                        new double[][]{
                                {0,0,1,0},
                                {0,0,0,10},
                                {0,-1,0,0},
                                {11,0,0,0},
                        })

            );
    }

}
