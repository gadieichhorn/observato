package com.rds.observato.skills;

import static org.junit.jupiter.api.Assertions.*;

import com.rds.observato.DatabaseTestBase;
import com.rds.observato.Fixtures;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class SkillsDaoTest extends DatabaseTestBase {

  private final long user = Fixtures.createUser(repository);

  @Test
  void insert() {
    long account = Fixtures.createAccount(repository, user);
    long skl = repository.skills().create(account, "skl0001", "reading");
    Assertions.assertThat(skl).isGreaterThan(0);
  }
}
