package com.rds.observato.view;

import com.rds.observato.projects.ProjectRecord;
import io.dropwizard.views.common.View;

public class ProjectView extends View {
  private final ProjectRecord project;

  public ProjectView(ProjectRecord project) {
    super("project.ftl");
    this.project = project;
  }

  public ProjectRecord getProject() {
    return project;
  }
}
