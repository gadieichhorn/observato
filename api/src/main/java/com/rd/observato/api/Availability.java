package com.rd.observato.api;

import java.time.Duration;
import java.time.Instant;

public interface Availability {

  Instant from();

  Instant to();

  Duration duration();
}
