package com.rd.observato.api;

import java.util.Set;

public interface Schedule {

  Set<Task> tasks();

  Set<Resource> resources();

  Set<Assignment> assignments();

  Set<Rule> rules();
}
