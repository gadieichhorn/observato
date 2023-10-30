package com.rds.observato.core;

import com.rds.observato.api.model.*;
import java.util.Map;
import java.util.Set;

public record InternalAgent(
    Account account,
    String key,
    Location base,
    Map<Skill, Integer> skills,
    Set<Tool> tools,
    Calendar calendar)
    implements Agent {}
