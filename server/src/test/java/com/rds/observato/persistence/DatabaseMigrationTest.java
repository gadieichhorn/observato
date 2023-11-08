package com.rds.observato.persistence;

import com.rds.observato.DatabaseTestBase;
import java.sql.SQLException;
import org.jdbi.v3.core.Jdbi;
import org.junit.jupiter.api.Test;

public class DatabaseMigrationTest extends DatabaseTestBase {

  @Test
  void name() throws SQLException {
    Jdbi jdbi = jdbi();
  }
}
