package com.rds.observato.api.model;

import java.util.Map;
import java.util.Set;

public interface Task {

  Location location();

  Calendar calendar();

  /**
   * SKill match with a minimum level
   *
   * @return
   */
  Map<Skill, Integer> skills();

  Set<Tool> tools();

  String description();
}
