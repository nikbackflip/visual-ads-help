package com.backflip.vadsh.controller.tasks;

import com.backflip.vadsh.controller.dto.ResponseDto;
import com.backflip.vadsh.ds.graph.task.Task;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TasksResponse extends ResponseDto {

    private List<AvailableTask> availableTasks;

    public static Builder builder() {
        return new Builder();
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    private static class AvailableTask {
        private String label;
        private String id;
        private List<Task.TaskParameter> parameters;
    }


    public static class Builder {

        private final List<AvailableTask> tasks = new LinkedList<>();

        public Builder withTask(Task task) {
            tasks.add(new AvailableTask(task.getLabel(), task.getId(), task.getTaskParameters()));
            return this;
        }

        public TasksResponse build() {
            return new TasksResponse(tasks);
        }
    }

}
