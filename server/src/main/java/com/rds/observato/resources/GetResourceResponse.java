package com.rds.observato.resources;

public record GetResourceResponse() {
  public static GetResourceResponse from(ResourceView resourceView) {
    return new GetResourceResponse();
  }
}
