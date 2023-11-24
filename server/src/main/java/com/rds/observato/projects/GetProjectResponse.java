package com.rds.observato.projects;

public record GetProjectResponse(long id, long account, String name, String description) {

  public static GetProjectResponse from(ProjectView view) {
    return new GetProjectResponse(view.id(), view.account(), view.name(), view.description());
  }
}
