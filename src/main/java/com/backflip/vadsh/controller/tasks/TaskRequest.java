package com.backflip.vadsh.controller.tasks;

import com.backflip.vadsh.controller.dto.GraphRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class TaskRequest {
    private Map<String, Integer> params;
    private GraphRequest graph;
}