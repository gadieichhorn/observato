package com.rds.observato.view;

import com.rds.observato.tasks.TaskRecord;
import io.dropwizard.views.common.View;

public class TaskView extends View {
  private final TaskRecord task;

  public TaskView(TaskRecord task) {
    super("task.ftl");
    this.task = task;
  }

  public TaskRecord getTask() {
    return task;
  }
}
