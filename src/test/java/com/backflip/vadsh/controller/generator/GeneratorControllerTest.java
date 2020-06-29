package com.backflip.vadsh.controller.generator;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static com.backflip.vadsh.test.ResourceUtils.fromFile;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class GeneratorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Disabled
    public void generateFromValidModel() throws Exception {
        mockMvc.perform(post("/generator/graph")
                .contentType(APPLICATION_JSON)
                .content(fromFile("graph/valid_model.json")))

                .andExpect(status().isOk())
                .andExpect(content().contentType("application/plain"))
                .andExpect(header().string("Content-Disposition", "attachment; filename=Graph.java"))
                .andExpect(content().bytes(fromFile("graph/ValidGraph.java")));
    }

    @Test
    @Disabled
    public void generateFromValidWithSkippedIds() throws Exception {
        mockMvc.perform(post("/generator/graph")
                .contentType(APPLICATION_JSON)
                .content(fromFile("graph/valid_model_skipped_ids.json")))

                .andExpect(status().isOk())
                .andExpect(content().contentType("application/plain"))
                .andExpect(header().string("Content-Disposition", "attachment; filename=Graph.java"))
                .andExpect(content().bytes(fromFile("graph/ValidGraph.java")));
    }


}

