package com.rds.observato.resources;

import java.util.Set;

public record GetResourcesResponse(Set<GetResourceResponse> resources) {}
