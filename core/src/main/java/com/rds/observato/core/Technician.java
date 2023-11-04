package com.rds.observato.core;

import com.rds.observato.api.model.*;
import java.util.Map;
import java.util.Set;

public record Technician(
    Account account,
    String key,
    Location base,
    Calendar calendar,
    Map<Skill, Integer> skills,
    Set<Tool> tools)
    implements Agent {}
