package com.rds.observato.projects;

import java.util.Set;

public record GetProjectsResponse(Set<GetProjectResponse> projects) {}
