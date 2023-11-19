package com.rds.observato.tasks;

import java.util.Set;

public record GetTasksResponse(Set<GetTaskResponse> tasks) {}
