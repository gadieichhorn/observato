package com.rds.observato.assignments;

import java.util.Set;

public record GetAssignmentsResponse(Set<GetAssignmentResponse> assignments) {}
