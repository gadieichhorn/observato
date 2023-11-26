package com.rds.observato.model;

import com.rd.observato.api.Availability;
import java.time.Instant;
import java.util.Objects;

public record SimpleAvailability(Instant from, Instant to) implements Availability {

  public SimpleAvailability {
    from = Objects.requireNonNull(from);
    to = Objects.requireNonNull(to);
  }
}
