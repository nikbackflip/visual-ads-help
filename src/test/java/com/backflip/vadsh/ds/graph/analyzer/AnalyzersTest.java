package com.backflip.vadsh.ds.graph.analyzer;

import com.backflip.vadsh.ds.graph.Config;
import com.backflip.vadsh.ds.graph.Edge;
import com.backflip.vadsh.ds.graph.Graph;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnalyzersTest {

    @ParameterizedTest
    @MethodSource("input")
    public void analyzerTest(Analyzer analyzer, Graph graph, Config config, boolean expected) {
        //given

        //when
        boolean result = analyzer.analyze(graph, config);

        //then
        assertEquals(result, expected);
    }

    private static Stream<Arguments> input() {
        return Stream.of(
                Arguments.of(weightedAnalyzer, singleNode, weightedConfig, true),
                Arguments.of(weightedAnalyzer, singleNode, notWeightedConfig, false),

                Arguments.of(directionalAnalyzer, singleNode, directionalConfig, true),
                Arguments.of(directionalAnalyzer, singleNode, notDirectionalConfig, false),

                Arguments.of(completeAnalyzer, completeGraphWithSelfLoops, selfLoopConfig, true),
                Arguments.of(completeAnalyzer, completeGraphWithoutSelfLoops, notSelfLoopConfig, true),
                Arguments.of(completeAnalyzer, completeGraphWithSelfLoops, notSelfLoopConfig, false),
                Arguments.of(completeAnalyzer, completeGraphWithoutSelfLoops, selfLoopConfig, false),
                Arguments.of(completeAnalyzer, incompleteGraph, notSelfLoopConfig, false),
                Arguments.of(completeAnalyzer, singleNodeSelfLoop, selfLoopConfig, true),
                Arguments.of(completeAnalyzer, singleNode, selfLoopConfig, false),
                Arguments.of(completeAnalyzer, singleNode, notSelfLoopConfig, true),

                Arguments.of(treeAnalyzer, singleNode, directionalConfig, true),
                Arguments.of(treeAnalyzer, singleNodeSelfLoop, directionalConfig, false),
                Arguments.of(treeAnalyzer, tree, directionalConfig, true),
                Arguments.of(treeAnalyzer, treeWithFreeNode, directionalConfig, false),
                Arguments.of(treeAnalyzer, completeGraphWithSelfLoops, directionalConfig, false),
                Arguments.of(treeAnalyzer, treeNotDirectional, directionalConfig, false),
                Arguments.of(treeAnalyzer, singleNode, notDirectionalConfig, true),
                Arguments.of(treeAnalyzer, singleNodeSelfLoop, notDirectionalConfig, false),
                Arguments.of(treeAnalyzer, tree, notDirectionalConfig, true),
                Arguments.of(treeAnalyzer, treeWithFreeNode, notDirectionalConfig, false),
                Arguments.of(treeAnalyzer, completeGraphWithSelfLoops, notDirectionalConfig, false),
                Arguments.of(treeAnalyzer, treeNotDirectional, notDirectionalConfig, true),

                Arguments.of(dagAnalyzer, singleNode, directionalConfig, true),
                Arguments.of(dagAnalyzer, singleNodeSelfLoop, directionalConfig, false),
                Arguments.of(dagAnalyzer, completeGraphWithSelfLoops, directionalConfig, false),
                Arguments.of(dagAnalyzer, dag, directionalConfig, true),
                Arguments.of(dagAnalyzer, dag, notDirectionalConfig, false),
                Arguments.of(dagAnalyzer, dagWithFreeNode, directionalConfig, true),
                Arguments.of(dagAnalyzer, singleNode, notDirectionalConfig, false),

                Arguments.of(negativeCyclesAnalyzer, singleNode, defaultConfig, false),
                Arguments.of(negativeCyclesAnalyzer, singleNodeSelfLoop, defaultConfig, false),
                Arguments.of(negativeCyclesAnalyzer, singleNodeSelfLoopNegative, defaultConfig, true),
                Arguments.of(negativeCyclesAnalyzer, completeGraphWithoutSelfLoops, defaultConfig, false),
                Arguments.of(negativeCyclesAnalyzer, negativeCycle, defaultConfig, true),
                Arguments.of(negativeCyclesAnalyzer, unreachableNegativeCycle, defaultConfig, true),

                Arguments.of(disconnectedAnalyzer, singleNode, defaultConfig, false),
                Arguments.of(disconnectedAnalyzer, completeGraphWithSelfLoops, defaultConfig, false),
                Arguments.of(disconnectedAnalyzer, disconnectedComponent, defaultConfig, true),
                Arguments.of(disconnectedAnalyzer, singleDisconnectedNode, defaultConfig, true),
                Arguments.of(disconnectedAnalyzer, componentUnreachableFromZero, defaultConfig, false)
        );
    }

    private final static Analyzer weightedAnalyzer = new WeightedAnalyzer();
    private final static Analyzer directionalAnalyzer = new DirectionalAnalyzer();
    private final static Analyzer completeAnalyzer = new CompleteAnalyzer();
    private final static Analyzer treeAnalyzer = new TreeAnalyzer();
    private final static Analyzer dagAnalyzer = new DagAnalyzer();
    private final static Analyzer negativeCyclesAnalyzer = new NegativeCyclesAnalyzer();
    private final static Analyzer disconnectedAnalyzer = new DisconnectedAnalyzer();

    private final static Config defaultConfig = new Config(true, true, true);
    private final static Config weightedConfig = new Config(false, true, false);
    private final static Config notWeightedConfig = new Config(true, false, true);
    private final static Config directionalConfig = new Config(true, false, false);
    private final static Config notDirectionalConfig = new Config(false, true, true);
    private final static Config selfLoopConfig = new Config(false, false, true);
    private final static Config notSelfLoopConfig = new Config(true, true, false);

    private final static Graph completeGraphWithSelfLoops = new Graph(List.of(new Edge(0, 0, 1), new Edge(0, 1, 1), new Edge(0, 2, 1), new Edge(1, 0, 1), new Edge(1, 1, 1), new Edge(1, 2, 1), new Edge(2, 0, 1), new Edge(2, 1, 1), new Edge(2, 2, 1)), 3);
    private final static Graph completeGraphWithoutSelfLoops = new Graph(List.of(new Edge(0, 1, 1), new Edge(0, 2, 1), new Edge(1, 0, 1), new Edge(1, 2, 1), new Edge(2, 0, 1), new Edge(2, 1, 1)), 3);
    private final static Graph incompleteGraph = new Graph(List.of(new Edge(0, 1, 1), new Edge(1, 0, 1), new Edge(1, 2, 1), new Edge(2, 0, 1), new Edge(2, 1, 1)), 3);
    private final static Graph singleNodeSelfLoop = new Graph(List.of(new Edge(0, 0, 1)), 1);
    private final static Graph singleNodeSelfLoopNegative = new Graph(List.of(new Edge(0, 0, -1)), 1);
    private final static Graph singleNode = new Graph(emptyList(), 1);
    private final static Graph tree = new Graph(List.of(new Edge(0, 1, 1.0), new Edge(0, 2, 1.0), new Edge(2, 3, 1.0), new Edge(2, 4, 1.0), new Edge(2, 5, 1.0)), 6);
    private final static Graph treeNotDirectional = new Graph(List.of(new Edge(0, 1, 1.0), new Edge(0, 2, 1.0), new Edge(2, 3, 1.0), new Edge(2, 4, 1.0), new Edge(2, 5, 1.0), new Edge(1, 0, 1.0), new Edge(2, 0, 1.0), new Edge(3, 2, 1.0), new Edge(4, 2, 1.0), new Edge(5, 2, 1.0)), 6);
    private final static Graph treeWithFreeNode = new Graph(List.of(new Edge(0, 1, 1.0), new Edge(0, 2, 1.0), new Edge(2, 3, 1.0), new Edge(2, 4, 1.0), new Edge(2, 5, 1.0)), 7);
    private final static Graph dag = new Graph(List.of(new Edge(0, 1, 1.0), new Edge(1, 3, 1.0), new Edge(3, 8, 1.0), new Edge(8, 10, 1.0), new Edge(0, 4, 1.0), new Edge(4, 7, 1.0), new Edge(7, 9, 1.0), new Edge(0, 2, 1.0), new Edge(2, 8, 1.0), new Edge(4, 5, 1.0)), 11);
    private final static Graph dagWithFreeNode = new Graph(List.of(new Edge(0, 1, 1.0), new Edge(1, 3, 1.0), new Edge(3, 8, 1.0), new Edge(8, 10, 1.0), new Edge(0, 4, 1.0), new Edge(4, 7, 1.0), new Edge(7, 9, 1.0), new Edge(0, 2, 1.0), new Edge(2, 8, 1.0), new Edge(4, 5, 1.0)), 12);
    private final static Graph negativeCycle = new Graph(List.of(new Edge(0, 2, 1.0), new Edge(2, 1, 1.0), new Edge(1, 0, -3.0), new Edge(2, 3, 1.0), new Edge(3, 5, 1.0), new Edge(3, 4, 1.0), new Edge(4, 2, 1.0)), 6);
    private final static Graph unreachableNegativeCycle = new Graph(List.of(new Edge(0, 1, 1.0), new Edge(1, 3, 1.0), new Edge(3, 0, 1.0), new Edge(2, 2, -1.0)), 4);
    private final static Graph disconnectedComponent = new Graph(List.of(new Edge(0, 0, 1.0), new Edge(0, 5, 1.0), new Edge(1, 3, 1.0), new Edge(2, 3, 6.0), new Edge(2, 6, -10.0), new Edge(3, 1, 10.0), new Edge(3, 2, 6.0), new Edge(3, 4, 1.0), new Edge(4, 0, 3.0), new Edge(5, 1, 1.0), new Edge(6, 0, 1.0), new Edge(6, 2, 10.0), new Edge(6, 6, 7.0), new Edge(7, 8, 1.0), new Edge(8, 9, 1.0), new Edge(9, 7, 1.0)), 10);
    private final static Graph singleDisconnectedNode = new Graph(List.of(new Edge(1, 2, 1.0),new Edge(2, 1, 1.0),new Edge(1, 3, 1.0),new Edge(3, 2, 1.0)), 4);
    private final static Graph componentUnreachableFromZero = new Graph(List.of(new Edge(0, 2, 1.0), new Edge(2, 3, 1.0), new Edge(3, 0, 1.0), new Edge(0, 1, 1.0), new Edge(1, 8, 1.0), new Edge(8, 0, 1.0), new Edge(0, 4, 1.0), new Edge(4, 8, 1.0), new Edge(8, 7, 1.0), new Edge(1, 1, 1.0), new Edge(7, 1, 1.0), new Edge(1, 6, 1.0), new Edge(6, 8, 1.0), new Edge(5, 8, 1.0), new Edge(3, 6, 1.0)), 9);
}


