package com.backflip.vadsh.controller.tasks;

import com.backflip.vadsh.ds.graph.Config;
import com.backflip.vadsh.ds.graph.Edge;
import com.backflip.vadsh.ds.graph.Graph;
import com.backflip.vadsh.ds.graph.task.TaskDefinition;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

import static com.backflip.vadsh.ds.graph.task.TaskDefinition.fromId;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Log4j2
@Controller
@RequestMapping("/tasks")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class TasksController {

    @ResponseBody
    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public TasksResponse availableTasks() {
        TasksResponse.Builder responseBuilder = TasksResponse.builder();
        Arrays.stream(TaskDefinition.values()).forEach(responseBuilder::withTask);

        return responseBuilder.build();
    }

    @ResponseBody
    @PostMapping(path = "/{taskId}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public void executeTasks(@RequestBody TaskRequest taskRequest, @PathVariable String taskId) {
        List<Edge> edges = taskRequest.getGraph().getEdges().stream()
                .map(em -> new Edge(em.getFromId(), em.getToId(), em.getWeight()))
                .collect(toList());
        Graph graph = new Graph(edges, taskRequest.getGraph().getNodes().size());
        Config config = new Config(
                taskRequest.getGraph().getConfig().isGraphDirectional(),
                taskRequest.getGraph().getConfig().isGraphWeighted(),
                taskRequest.getGraph().getConfig().isSelfLoopsAllowed());

        TaskDefinition requestedTask = fromId(taskId);

        requestedTask.execute(graph, config, taskRequest.getParams());
    }
}
