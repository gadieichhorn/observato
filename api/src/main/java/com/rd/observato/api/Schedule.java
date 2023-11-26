package com.rd.observato.api;

import java.time.Instant;
import java.util.Map;
import java.util.Set;

public interface Schedule {

  Instant start();

  Instant end();

  Set<Task> tasks();

  Map<Resource, Set<Assignment>> assignments();
}
