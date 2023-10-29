package com.rds.observato.api.model;

import java.util.Map;
import java.util.Set;

public interface Agent {

  Location base();

  Calendar calendar();

  /**
   * SKill match by a level
   *
   * @return
   */
  Map<Skill, Integer> skills();

  Set<Tool> tools();
}
