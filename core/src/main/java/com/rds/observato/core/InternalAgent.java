package com.rds.observato.core;

import com.rds.observato.api.Agent;
import com.rds.observato.api.Location;
import com.rds.observato.api.Skill;
import com.rds.observato.api.Tool;
import java.util.Map;
import java.util.Set;

public record InternalAgent(Location base, Map<Skill, Integer> skills, Set<Tool> tools)
    implements Agent {

  public InternalAgent {
    // TODO VALIDATION
  }
}
