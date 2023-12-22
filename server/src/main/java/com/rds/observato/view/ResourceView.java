package com.rds.observato.view;

import com.rds.observato.resources.ResourceRecord;
import io.dropwizard.views.common.View;

public class ResourceView extends View {
  private final ResourceRecord resource;

  public ResourceView(ResourceRecord resource) {
    super("resource.ftl");
    this.resource = resource;
  }

  public ResourceRecord getResource() {
    return resource;
  }
}
