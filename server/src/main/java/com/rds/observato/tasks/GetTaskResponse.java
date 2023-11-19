package com.rds.observato.tasks;

public record GetTaskResponse(
    long id, int revision, long account, String name, String description) {
  public static GetTaskResponse from(TaskView view) {
    return new GetTaskResponse(
        view.id(), view.revision(), view.account(), view.name(), view.description());
  }
}
