package com.backflip.vadsh.controller.analytics;

import com.backflip.vadsh.ds.graph.analyzer.AnalyticDefinition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;


import static com.backflip.vadsh.test.ResourceUtils.fromFile;
import static org.hamcrest.collection.IsIterableWithSize.iterableWithSize;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
public class AnalyticsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void generateFromValidModel() throws Exception {
        mockMvc.perform(post("/analytics")
                .contentType(APPLICATION_JSON)
                .content(fromFile("graph/valid_model.json")))

                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.analytics", iterableWithSize(AnalyticDefinition.values().length)));
    }

}
