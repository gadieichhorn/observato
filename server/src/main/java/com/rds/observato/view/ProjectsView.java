package com.rds.observato.view;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.rds.observato.projects.ProjectRecord;
import io.dropwizard.views.common.View;
import java.util.Set;

public class ProjectsView extends View {
  private final Set<ProjectRecord> projects;

  @JsonCreator
  public ProjectsView(Set<ProjectRecord> projects) {
    super("projects.ftl");
    this.projects = projects;
  }

  public Set<ProjectRecord> getProjects() {
    return projects;
  }
}
