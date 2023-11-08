package com.rds.observato.persistence;

import com.rds.observato.DatabaseTestBase;
import java.sql.SQLException;
import org.assertj.core.api.Assertions;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.junit.jupiter.api.Test;

public class DatabaseJdbiTest extends DatabaseTestBase {

  @Test
  void name() throws SQLException {
    Jdbi jdbi = jdbi();
    Handle open = jdbi.open();
    Assertions.assertThat(open).isNotNull();
  }
}
