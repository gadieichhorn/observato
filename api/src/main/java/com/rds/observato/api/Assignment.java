package com.rds.observato.api;

import java.time.Duration;
import java.time.Instant;
import java.util.Set;

public interface Assignment {

  Task task();

  Set<Agent> agents();

  Instant start();

  Duration duration();
}
