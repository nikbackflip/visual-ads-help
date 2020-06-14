package com.backflip.vadsh.template.graph;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GeneralGraphTest extends AbstractGraphTest {

    @BeforeAll
    static void init() throws IOException {
        Map<String, String> args = Map.of(
                "edgesList", ""
                ,
                "nodeIdToNameMap", "" +
                        "put(0, \"node0\");" +
                        "put(1, \"node1\");" +
                        "put(2, \"node2\");" +
                        "put(3, \"node3\");"
        );
        init(args);
    }

    @Test
    public void getNameByNodeId() {
        //given

        //when
        String nodeName = graphReflect.getNodeName(1);

        //then
        assertEquals("node1", nodeName);
    }

    @Test
    public void getNodesCount() {
        //given

        //when
        int nodesCount = graphReflect.nodesCount();

        //then
        assertEquals(4, nodesCount);
    }
}
