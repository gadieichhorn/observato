package com.rds.observato.persistence;

import com.rds.observato.DatabaseTestBase;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;

public class DatabaseMigrationTest extends DatabaseTestBase {

  @Test
  void name() throws SQLException {
    //    Connection connection = datasource().getConnection();
    migrate(datasource());
  }
}
