package com.rds.observato.users;

import static org.junit.jupiter.api.Assertions.*;

import com.rds.observato.DatabaseTestBase;
import com.rds.observato.api.persistence.Repository;
import org.assertj.core.api.Assertions;
import org.jdbi.v3.core.statement.UnableToExecuteStatementException;
import org.junit.jupiter.api.Test;

class UsersDaoTest extends DatabaseTestBase {

  private static final Repository repository = repository();

  @Test
  void create() {
    Assertions.assertThatCode(
            () -> repository.users().create("usr001", "salt".getBytes(), "hash".getBytes()))
        .doesNotThrowAnyException();
  }

  @Test
  void duplicate() {
    repository.users().create("usr002", "salt".getBytes(), "hash".getBytes());
    Assertions.assertThatThrownBy(
            () -> repository.users().create("usr002", "salt".getBytes(), "hash".getBytes()))
        .isInstanceOf(UnableToExecuteStatementException.class);
  }

  @Test
  void findById() {
    long user = repository.users().create("usr003", "salt".getBytes(), "hash".getBytes());
    Assertions.assertThat(repository.users().findById(user))
        .isPresent()
        .get()
        .hasFieldOrPropertyWithValue("name", "usr003");
  }

  @Test
  void findByName() {
    long user = repository.users().create("usr004", "salt".getBytes(), "hash".getBytes());
    Assertions.assertThat(repository.users().findByName("usr004"))
        .isPresent()
        .get()
        .hasFieldOrPropertyWithValue("id", user);
  }
}
