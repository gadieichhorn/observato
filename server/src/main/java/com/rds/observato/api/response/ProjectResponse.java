package com.rds.observato.api.response;

import com.rds.observato.projects.ProjectView;

public record ProjectResponse(long id, long account, String name, String description) {

  public static ProjectResponse from(ProjectView view) {
    return new ProjectResponse(view.id(), view.account(), view.name(), view.description());
  }
}
