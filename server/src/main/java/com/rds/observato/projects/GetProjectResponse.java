package com.rds.observato.projects;

public record GetProjectResponse(
    long id, long account, int revision, String name, String description) {

  public static GetProjectResponse from(ProjectRecord view) {
    return new GetProjectResponse(
        view.id(), view.account(), view.revision(), view.name(), view.description());
  }
}
