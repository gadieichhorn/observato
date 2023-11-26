package com.rd.observato.api;

import java.time.Duration;
import java.util.Map;

public interface Task {
  String name();

  Duration duration();

  Map<Skill, Integer> skills();
}
