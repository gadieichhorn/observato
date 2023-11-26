package com.rd.observato.api;

import java.time.Instant;

public interface Availability {

  Instant from();

  Instant to();
}
