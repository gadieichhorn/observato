package com.rds.observato.schema;

import java.sql.Connection;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;

public class DatabaseMigrationTest extends DatabaseTestBase {

  @Test
  void name() throws SQLException {
    Connection connection = datasource().getConnection();
    migrate(datasource());
  }
}
