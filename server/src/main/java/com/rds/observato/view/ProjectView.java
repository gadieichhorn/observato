package com.rds.observato.view;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rds.observato.projects.ProjectRecord;
import io.dropwizard.views.common.View;

public class ProjectView extends View {
  private final ProjectRecord project;

  @JsonCreator
  public ProjectView(@JsonProperty("project") ProjectRecord project) {
    super("project.ftl");
    this.project = project;
  }

  public ProjectRecord getProject() {
    return project;
  }
}
