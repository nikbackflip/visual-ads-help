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
                .ofSize(4)

                .build();

        init(args);
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
