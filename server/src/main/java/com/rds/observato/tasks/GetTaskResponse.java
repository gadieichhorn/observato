package com.rds.observato.tasks;

import java.util.Map;

public record GetTaskResponse(
    long id,
    int revision,
    long account,
    String name,
    String description,
    Map<String, Object> skills) {
  public static GetTaskResponse from(TaskRecord view) {
    return new GetTaskResponse(
        view.id(), view.revision(), view.account(), view.name(), view.description(), view.skills());
  }
}
