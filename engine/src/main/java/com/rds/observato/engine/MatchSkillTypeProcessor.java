package com.rds.observato.engine;

import com.rd.observato.api.Skill;
import java.util.Map;

@FunctionalInterface
public interface MatchSkillTypeProcessor {

  boolean match(Map<Skill, Integer> rhs, Map<Skill, Integer> lhs);
}
