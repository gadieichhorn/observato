package com.rds.observato.model;

import com.rd.observato.api.Resource;
import com.rd.observato.api.Skill;
import java.util.Map;

public record SimpleResource(String name, Map<Skill, Integer> skills) implements Resource {}
