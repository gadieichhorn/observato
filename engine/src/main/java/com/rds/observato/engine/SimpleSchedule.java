package com.rds.observato.engine;

import com.rd.observato.api.*;
import java.time.Instant;
import java.util.Map;
import java.util.Set;

public record SimpleSchedule(
    Instant start, Instant end, Set<Task> tasks, Map<Resource, Set<Assignment>> assignments)
    implements Schedule {}
