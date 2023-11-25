package com.rds.observato.engine;

import com.rd.observato.api.*;
import java.util.Set;

public record SimpleSchedule(
    Set<Task> tasks, Set<Resource> resources, Set<Assignment> assignments, Set<Rule> rules)
    implements Schedule {}
