package com.rd.observato.api;

import java.time.Instant;
import java.util.Set;

public interface Schedule {

  Instant start();

  Instant end();

  Set<Task> tasks();

  Set<Resource> resources();

  Set<Assignment> assignments();
}
