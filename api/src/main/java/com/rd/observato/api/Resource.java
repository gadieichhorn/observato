package com.rd.observato.api;

import java.util.Map;

public interface Resource {
  String name();

  Map<Skill, Integer> skills();
}
