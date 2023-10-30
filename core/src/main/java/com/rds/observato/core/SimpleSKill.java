package com.rds.observato.core;

import com.rds.observato.api.model.Skill;

public record SimpleSKill(String name, String description) implements Skill {}
