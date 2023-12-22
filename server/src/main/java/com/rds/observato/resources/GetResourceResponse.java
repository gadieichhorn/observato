package com.rds.observato.resources;

import java.util.Map;

public record GetResourceResponse(long id, long account, String name, Map<String, Object> skills) {
  public static GetResourceResponse from(ResourceRecord view) {
    return new GetResourceResponse(view.id(), view.account(), view.name(), view.skills());
  }
}
