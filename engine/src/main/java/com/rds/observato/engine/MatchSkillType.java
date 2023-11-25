package com.rds.observato.engine;

import com.rd.observato.api.Skill;
import java.util.Map;

public enum MatchSkillType {
  EXACTLY(new MatchAtExactlySkillTypeProcessor()),
  AT_MOST(new MatchAtMostSkillTypeProcessor()),
  AT_LEAST(new MatchAtLeastSkillTypeProcessor());

  public final MatchSkillTypeProcessor matcher;

  MatchSkillType(MatchSkillTypeProcessor matcher) {
    this.matcher = matcher;
  }

  private record MatchAtLeastSkillTypeProcessor() implements MatchSkillTypeProcessor {
    @Override
    public boolean match(Map<Skill, Integer> rhs, Map<Skill, Integer> lhs) {
      boolean containsAllKeysAndValues = true;

      for (Skill key : rhs.keySet()) {
        if (!lhs.containsKey(key) || lhs.get(key) < (rhs.get(key))) {
          containsAllKeysAndValues = false;
          break;
        }
      }
      return containsAllKeysAndValues;
    }
  }

  private record MatchAtMostSkillTypeProcessor() implements MatchSkillTypeProcessor {
    @Override
    public boolean match(Map<Skill, Integer> rhs, Map<Skill, Integer> lhs) {
      boolean containsAllKeysAndValues = true;

      for (Skill key : rhs.keySet()) {
        if (!lhs.containsKey(key) || lhs.get(key) > (rhs.get(key))) {
          containsAllKeysAndValues = false;
          break;
        }
      }
      return containsAllKeysAndValues;
    }
  }

  private record MatchAtExactlySkillTypeProcessor() implements MatchSkillTypeProcessor {
    @Override
    public boolean match(Map<Skill, Integer> rhs, Map<Skill, Integer> lhs) {
      boolean containsAllKeysAndValues = true;

      for (Skill key : rhs.keySet()) {
        if (!lhs.containsKey(key) || !lhs.get(key).equals(rhs.get(key))) {
          containsAllKeysAndValues = false;
          break;
        }
      }
      return containsAllKeysAndValues;
    }
  }
}
