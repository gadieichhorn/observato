package com.rds.observato.model;

import com.rd.observato.api.Skill;

public record SimpleSkill(String name, String description) implements Skill {}
