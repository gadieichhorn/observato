package com.rds.observato.auth;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import com.rds.observato.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.testcontainers.shaded.com.google.common.collect.ImmutableSet;

class UserTest {

  @Test
  void valid() {
    assertThatCode(() -> new User(1, 1, "a", ImmutableSet.of())).doesNotThrowAnyException();
  }

  @Test
  void invalid() {
    assertThatThrownBy(() -> new User(0, 1, "", ImmutableSet.of()))
        .isInstanceOf(ValidationException.class);
    assertThatThrownBy(() -> new User(1, 0, "", ImmutableSet.of()))
        .isInstanceOf(ValidationException.class);
    assertThatThrownBy(() -> new User(1, 1, null, ImmutableSet.of()))
        .isInstanceOf(ValidationException.class);
    assertThatThrownBy(() -> new User(1, 1, "", ImmutableSet.of()))
        .isInstanceOf(ValidationException.class);
    assertThatThrownBy(() -> new User(1, 1, "a", null)).isInstanceOf(ValidationException.class);
  }
}
