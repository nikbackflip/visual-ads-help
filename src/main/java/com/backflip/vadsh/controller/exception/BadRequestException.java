package com.backflip.vadsh.controller.exception;

import com.backflip.vadsh.ds.graph.task.TaskExecutionFailed;
import com.backflip.vadsh.ds.graph.task.TaskResult;

public class BadRequestException extends Exception {

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException() {
    }

    public static BadRequestException badRequest(TaskResult error) {
        if (error instanceof TaskExecutionFailed) {
            return new BadRequestException(((TaskExecutionFailed) error).getMessage());
        }
        return new BadRequestException();
    }
}
