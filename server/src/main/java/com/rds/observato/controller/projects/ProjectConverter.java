package com.rds.observato.controller.projects;

import com.rds.observato.api.ResponseMapper;
import com.rds.observato.api.response.ProjectResponse;
import com.rds.observato.persistence.projects.ProjectView;

public class ProjectConverter implements ResponseMapper<ProjectView, ProjectResponse> {

  @Override
  public ProjectResponse convert(ProjectView view) {
    return new ProjectResponse(view.id(), view.account(), view.name(), view.description());
  }
}
