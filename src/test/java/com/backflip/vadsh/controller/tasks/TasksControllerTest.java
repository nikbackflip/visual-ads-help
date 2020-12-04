package com.backflip.vadsh.controller.tasks;

import com.backflip.vadsh.ds.graph.task.TaskDefinition;
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

@SpringBootTest
@AutoConfigureMockMvc
public class TasksControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void generateFromValidModel() throws Exception {
        mockMvc.perform(post("/tasks")
                .contentType(APPLICATION_JSON)
                .content(fromFile("graph/valid_model.json")))

                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.availableTasks", iterableWithSize(TaskDefinition.values().length)));
    }

}
