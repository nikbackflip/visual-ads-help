package com.backflip.vadsh.ds.graph.task;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TaskExecutionFailed extends TaskResult {

    public TaskExecutionFailed(String message) {
        this.message = message;
        this.success = false;
    }

    private final String message;

}
