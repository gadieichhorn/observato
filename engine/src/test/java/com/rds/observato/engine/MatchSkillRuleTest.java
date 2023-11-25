package com.rds.observato.engine;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import com.google.common.collect.ImmutableMap;
import com.rd.observato.api.Resource;
import com.rd.observato.api.Rule;
import com.rd.observato.api.Skill;
import com.rd.observato.api.Task;
import com.rds.observato.model.SimpleResource;
import com.rds.observato.model.SimpleSkill;
import com.rds.observato.model.SimpleTask;
import org.junit.jupiter.api.Test;

class MatchSkillRuleTest {

  Skill s1 = new SimpleSkill("S1", "S1");
  Skill s2 = new SimpleSkill("S2", "S2");
  Skill s3 = new SimpleSkill("S3", "S3");
  Task t1 = new SimpleTask("T1", ImmutableMap.of(s1, 1));
  Task t2 = new SimpleTask("T2", ImmutableMap.of(s2, 2));
  Task t3 = new SimpleTask("T3", ImmutableMap.of(s3, 3));
  Resource r1 = new SimpleResource("R1", ImmutableMap.of(s1, 10));
  Resource r2 = new SimpleResource("R1", ImmutableMap.of(s2, 2, s1, 1, s3, 3));
  Resource r3 = new SimpleResource("R1", ImmutableMap.of(s2, 0, s1, 0, s3, 0));

  @Test
  void alLeast() {
    Rule rule = new MatchSkillRule(MatchSkillType.AT_LEAST);
    assertThat(rule.test(t1, r1)).isTrue();
    assertThat(rule.test(t2, r1)).isFalse();
    assertThat(rule.test(t3, r1)).isFalse();
    assertThat(rule.test(t1, r2)).isTrue();
    assertThat(rule.test(t2, r2)).isTrue();
    assertThat(rule.test(t3, r2)).isTrue();
    assertThat(rule.test(t1, r3)).isFalse();
    assertThat(rule.test(t2, r3)).isFalse();
    assertThat(rule.test(t3, r3)).isFalse();
  }

  @Test
  void atMost() {
    Rule rule = new MatchSkillRule(MatchSkillType.AT_MOST);
    assertThat(rule.test(t1, r1)).isFalse();
    assertThat(rule.test(t2, r1)).isFalse();
    assertThat(rule.test(t3, r1)).isFalse();
    assertThat(rule.test(t1, r2)).isTrue();
    assertThat(rule.test(t2, r2)).isTrue();
    assertThat(rule.test(t3, r2)).isTrue();
    assertThat(rule.test(t1, r3)).isTrue();
    assertThat(rule.test(t2, r3)).isTrue();
    assertThat(rule.test(t3, r3)).isTrue();
  }

  @Test
  void exactly() {
    Rule rule = new MatchSkillRule(MatchSkillType.EXACTLY);
    assertThat(rule.test(t1, r1)).isFalse();
    assertThat(rule.test(t2, r1)).isFalse();
    assertThat(rule.test(t3, r1)).isFalse();
    assertThat(rule.test(t1, r2)).isTrue();
    assertThat(rule.test(t2, r2)).isTrue();
    assertThat(rule.test(t3, r2)).isTrue();
    assertThat(rule.test(t1, r3)).isFalse();
    assertThat(rule.test(t2, r3)).isFalse();
    assertThat(rule.test(t3, r3)).isFalse();
  }
}
