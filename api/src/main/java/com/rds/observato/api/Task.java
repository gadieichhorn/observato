package com.rds.observato.api;

import java.util.Map;
import java.util.Set;

public interface Task {

  Location location();

  /**
   * SKill match with a minimum level
   *
   * @return
   */
  Map<Skill, Integer> skills();

  Set<Tool> tools();

  String description();
}
