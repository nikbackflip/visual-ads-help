package com.backflip.vadsh.controller.layout;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static com.backflip.vadsh.test.ResourceUtils.fromFile;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class LayoutControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void layoutGraph() throws Exception {
        mockMvc.perform(post("/layout")
                .contentType(APPLICATION_JSON)
                .content(fromFile("graph/valid_model.json"))
                .queryParam("x", "1000")
                .queryParam("y", "1000"))

                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.coordinates", notNullValue()));
    }
}