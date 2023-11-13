package com.rds.observato.resources;

import static org.junit.jupiter.api.Assertions.*;

import com.rds.observato.DatabaseTestBase;
import com.rds.observato.Fixtures;
import com.rds.observato.api.persistence.Repository;
import java.util.Set;
import org.assertj.core.api.Assertions;
import org.jdbi.v3.core.statement.UnableToExecuteStatementException;
import org.junit.jupiter.api.Test;

class ResourcesDaoTest extends DatabaseTestBase {

  private static final Repository repository = repository();
  private static final long user = Fixtures.createUser(repository);

  @Test
  void create() {
    long account = Fixtures.createAccount(repository, user);
    Assertions.assertThatCode(() -> repository.resources().create(account, "r001"))
        .doesNotThrowAnyException();
  }

  @Test
  void duplicate() {
    long account = Fixtures.createAccount(repository, user);
    repository.resources().create(account, "r002");
    Assertions.assertThatThrownBy(() -> repository.resources().create(account, "r002"))
        .isInstanceOf(UnableToExecuteStatementException.class);
  }

  @Test
  void sameNameDifferentAccounts() {
    long account1 = Fixtures.createAccount(repository, user);
    long account2 = Fixtures.createAccount(repository, user);
    repository.resources().create(account1, "r002");
    repository.resources().create(account2, "r002");
  }

  @Test
  void getAll() {
    long account = Fixtures.createAccount(repository, user);
    long resource = repository.resources().create(account, "r003");
    Set<ResourceView> all = repository.resources().getAll(account);
    Assertions.assertThat(all).contains(new ResourceView(resource, account, "r003"));
  }
}
