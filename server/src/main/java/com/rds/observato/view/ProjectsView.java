package com.rds.observato.view;

import com.rds.observato.projects.ProjectRecord;
import io.dropwizard.views.common.View;
import java.util.Set;

public class ProjectsView extends View {
  private final Set<ProjectRecord> projects;

  public ProjectsView(Set<ProjectRecord> projects) {
    super("projects.ftl");
    this.projects = projects;
  }

  public Set<ProjectRecord> getProjects() {
    return projects;
  }
}
