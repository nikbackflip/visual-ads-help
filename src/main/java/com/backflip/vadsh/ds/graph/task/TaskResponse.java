package com.backflip.vadsh.ds.graph.task;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import com.backflip.vadsh.ds.graph.Edge;

import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@Builder
@Getter
public class TaskResponse {

    private final List<Integer> nodes;
    private final List<Edge> edges;

    public static TaskResponse emptyResponse() {
        return TaskResponse.builder().nodes(Collections.emptyList()).edges(Collections.emptyList()).build();
    }
}
