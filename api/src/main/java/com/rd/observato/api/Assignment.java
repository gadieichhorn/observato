package com.rd.observato.api;

import java.time.Instant;

public interface Assignment {

  Instant start();

  Instant end();

  Task task();

  Resource resource();
}
