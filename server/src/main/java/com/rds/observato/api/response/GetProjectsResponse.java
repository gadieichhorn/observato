package com.rds.observato.api.response;

import java.util.Set;

public record GetProjectsResponse(Set<GetProjectResponse> projects) {}
