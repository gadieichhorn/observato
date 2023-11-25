package com.rds.observato.model;

import com.rd.observato.api.Assignment;
import com.rd.observato.api.Resource;
import com.rd.observato.api.Task;
import java.time.Instant;

public record SimpleAssignment() implements Assignment {
  @Override
  public Instant start() {
    return null;
  }

  @Override
  public Instant end() {
    return null;
  }

  @Override
  public Task task() {
    return null;
  }

  @Override
  public Resource resource() {
    return null;
  }
}
