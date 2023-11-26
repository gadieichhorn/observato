package com.rds.observato.model;

import com.rd.observato.api.Assignment;
import com.rd.observato.api.Resource;
import com.rd.observato.api.Task;
import java.time.Instant;
import java.util.Objects;

public record SimpleAssignment(Task task, Resource resource, Instant start, Instant end)
    implements Assignment {

  public SimpleAssignment {
    task = Objects.requireNonNull(task);
  }
}
