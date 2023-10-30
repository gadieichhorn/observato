package com.rds.observato.core;

import com.rds.observato.api.model.*;
import java.util.Map;
import java.util.Set;

public record Job(
    Calendar calendar,
    Location location,
    Map<Skill, Integer> skills,
    Set<Tool> tools,
    String description)
    implements Task {}
