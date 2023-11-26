package com.rds.observato.model;

import com.rd.observato.api.Skill;
import com.rd.observato.api.Task;
import java.time.Duration;
import java.util.Map;

public record SimpleTask(String name, Duration duration, Map<Skill, Integer> skills)
    implements Task {}
