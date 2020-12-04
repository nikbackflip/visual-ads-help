package com.backflip.vadsh.ds.graph.task;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Builder
@Getter
public class TaskResponse {

    private final List<Integer> nodes;

}
