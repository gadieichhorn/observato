package com.rds.observato.projects;

import com.rds.observato.api.response.ProjectResponse;
import com.rds.observato.json.ResponseMapper;

public class ProjectConverter implements ResponseMapper<ProjectView, ProjectResponse> {

  @Override
  public ProjectResponse convert(ProjectView view) {
    return new ProjectResponse(view.id(), view.account(), view.name(), view.description());
  }
}
