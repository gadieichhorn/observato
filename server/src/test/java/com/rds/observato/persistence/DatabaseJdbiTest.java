package com.rds.observato.persistence;

import com.rds.observato.DatabaseTestBase;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.assertj.core.api.Assertions;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.junit.jupiter.api.Test;

public class DatabaseJdbiTest extends DatabaseTestBase {

  @Test
  void name() throws SQLException {
    DataSource datasource = datasource();
    migrate(datasource);
    Jdbi jdbi = jdbi(datasource.getConnection());
    Handle open = jdbi.open();
    Assertions.assertThat(open).isNotNull();
  }
}