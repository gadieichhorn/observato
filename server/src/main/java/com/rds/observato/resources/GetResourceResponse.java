package com.rds.observato.resources;

public record GetResourceResponse(long id, long account, String name) {
  public static GetResourceResponse from(ResourceView view) {
    return new GetResourceResponse(view.id(), view.account(), view.name());
  }
}
