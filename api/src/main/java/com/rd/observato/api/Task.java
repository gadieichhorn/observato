package com.rd.observato.api;

import java.util.Map;

public interface Task {
  String name();

  Map<Skill, Integer> skills();
}
