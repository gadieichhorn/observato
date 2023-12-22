package com.rds.observato.view;

import com.rds.observato.resources.ResourceRecord;
import io.dropwizard.views.common.View;
import java.util.Set;

public class ResourcesView extends View {
  private final Set<ResourceRecord> resources;

  public ResourcesView(Set<ResourceRecord> resources) {
    super("resources.ftl");
    this.resources = resources;
  }

  public Set<ResourceRecord> getResources() {
    return resources;
  }
}
