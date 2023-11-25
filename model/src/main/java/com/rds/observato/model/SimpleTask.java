package com.rds.observato.model;

import com.rd.observato.api.Skill;
import com.rd.observato.api.Task;
import java.util.Map;

public record SimpleTask(String name, Map<Skill, Integer> skills) implements Task {}
