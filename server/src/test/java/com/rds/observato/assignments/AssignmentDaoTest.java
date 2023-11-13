package com.rds.observato.assignments;

import static org.junit.jupiter.api.Assertions.*;

import com.rds.observato.DatabaseTestBase;
import com.rds.observato.Fixtures;
import com.rds.observato.api.persistence.Repository;
import org.junit.jupiter.api.Test;

class AssignmentDaoTest extends DatabaseTestBase {

  private static final Repository repository = repository();
  private static final long user = Fixtures.createUser(repository);

  @Test
  void create() {
    //    assertThatCode(() -> repository.assignments().create("a010",
    // user)).doesNotThrowAnyException();
  }
}
