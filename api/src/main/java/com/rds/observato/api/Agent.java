package com.rds.observato.api;

import java.util.Map;
import java.util.Set;

public interface Agent {

  Location base();

  /**
   * SKill match by a level
   *
   * @return
   */
  Map<Skill, Integer> skills();

  Set<Tool> tools();
}
