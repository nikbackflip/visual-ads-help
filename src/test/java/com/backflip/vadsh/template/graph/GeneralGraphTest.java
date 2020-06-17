package com.backflip.vadsh.template.graph;

import com.backflip.vadsh.templates.graph.GraphArgs;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.backflip.vadsh.templates.graph.GraphArgs.builder;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GeneralGraphTest extends AbstractGraphTest {

    @BeforeAll
    static void init() throws IOException {
        GraphArgs args = builder()
                .withNode(0, "node0")
                .withNode(1, "node1")
                .withNode(2, "node2")
                .withNode(3, "node3")

                .build();

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
