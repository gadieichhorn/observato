package com.rds.observato.core;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.rds.observato.api.model.Rule;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class MatchSkillRuleTest {
  @Test
  void equal() {
    Rule rule = new MatchSkillRule();
    Assertions.assertThat(
            rule.test(
                    new Job(
                        null,
                        null,
                        ImmutableMap.of(new SimpleSKill("A", "A"), 1),
                        ImmutableSet.of(),
                        ""),
                    new Technician(null, null, ImmutableMap.of(new SimpleSKill("A", "A"), 1), null))
                .get())
        .isTrue();
  }

  @Test
  void more() {
    Rule rule = new MatchSkillRule();
    Assertions.assertThat(
            rule.test(
                    new Job(
                        null,
                        null,
                        ImmutableMap.of(new SimpleSKill("A", "A"), 1),
                        ImmutableSet.of(),
                        ""),
                    new Technician(
                        null,
                        null,
                        ImmutableMap.of(new SimpleSKill("A", "A"), 1, new SimpleSKill("B", "B"), 1),
                        null))
                .get())
        .isTrue();
  }

  @Test
  void missing() {
    Rule rule = new MatchSkillRule();
    Assertions.assertThat(
            rule.test(
                    new Job(
                        null,
                        null,
                        ImmutableMap.of(new SimpleSKill("A", "A"), 1, new SimpleSKill("C", "C"), 2),
                        ImmutableSet.of(),
                        ""),
                    new Technician(
                        null,
                        null,
                        ImmutableMap.of(new SimpleSKill("A", "A"), 1, new SimpleSKill("B", "B"), 1),
                        null))
                .get())
        .isFalse();
  }
}
