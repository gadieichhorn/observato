package com.rds.observato.view;

import com.rds.observato.tasks.TaskRecord;
import io.dropwizard.views.common.View;
import java.util.Set;

public class TasksView extends View {
  private final Set<TaskRecord> tasks;

  public TasksView(Set<TaskRecord> tasks) {
    super("tasks.ftl");
    this.tasks = tasks;
  }

  public Set<TaskRecord> getTasks() {
    return tasks;
  }
}
