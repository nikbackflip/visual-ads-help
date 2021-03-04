package com.backflip.vadsh.controller.generator;

import com.backflip.vadsh.ds.graph.generator.GeneratorOption;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.collection.IsIterableWithSize.iterableWithSize;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class GeneratorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getAllGeneratorOptions() throws Exception {
        mockMvc.perform(get("/generator/options")
                .contentType(APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.availableOptions", iterableWithSize(GeneratorOption.allOptions().size())));
    }

    @Test
    public void getGeneratorOptions() throws Exception {
        mockMvc.perform(get("/generator/options")
                .contentType(APPLICATION_JSON)
                .queryParam("for", "WEIGHTED, DIRECTED"))

                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.availableOptions", not(empty())));
    }

    @Test
    public void generate() throws Exception {
        mockMvc.perform(get("/generator/generate")
                .contentType(APPLICATION_JSON)
                .queryParam("for", "WEIGHTED, DIRECTED, COMPLETE"))

                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.graph", not(empty())))
                .andExpect(jsonPath("$.config", not(empty())));
    }

    @Test
    public void generateWithLayout() throws Exception {
        mockMvc.perform(get("/generator/generate")
                .contentType(APPLICATION_JSON)
                .queryParam("for", "WEIGHTED, DIRECTED, COMPLETE")
                .queryParam("x", "100")
                .queryParam("y", "100"))

                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.graph", not(empty())))
                .andExpect(jsonPath("$.config", not(empty())))
                .andExpect(jsonPath("$.coordinates", notNullValue()));
    }
}