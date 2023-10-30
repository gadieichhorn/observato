package com.rds.observato.api.model;

import java.util.Map;
import java.util.Set;

public interface Agent {

  Account account();

  String key();

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
