package com.rds.observato.view;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rds.observato.tasks.TaskRecord;
import io.dropwizard.views.common.View;

public class TaskView extends View {
  private final TaskRecord task;

  @JsonCreator
  public TaskView(@JsonProperty("task") TaskRecord task) {
    super("task.ftl");
    this.task = task;
  }

  public TaskRecord getTask() {
    return task;
  }
}
