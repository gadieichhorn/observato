package com.rds.observato.assignments;

import java.time.Instant;

public record GetAssignmentResponse(
    long id, int revision, long account, long task, long resource, Instant start, Instant end) {
  public static GetAssignmentResponse from(AssignmentView view) {
    return new GetAssignmentResponse(
        view.id(),
        view.revision(),
        view.account(),
        view.task(),
        view.resource(),
        view.start(),
        view.end());
  }
}
