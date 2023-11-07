package com.rds.observato;

import com.rds.observato.persistence.DatabaseConfiguration;
import com.rds.observato.persistence.accounts.AccountView;
import java.sql.Connection;
import javax.sql.DataSource;
import org.flywaydb.core.Flyway;
import org.jdbi.v3.core.Jdbi;
// import org.jdbi.v3.postgres.PostgresPlugin;
import org.jdbi.v3.core.mapper.reflect.ConstructorMapper;
import org.jdbi.v3.postgres.PostgresPlugin;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.postgresql.ds.PGSimpleDataSource;

public class DatabaseTestBase {

  private static final DatabaseContainer DATABASE;

  static {
    DATABASE =
        DatabaseContainer.create(new DatabaseConfiguration("observato", "observato", "observato"));
  }

  public DataSource datasource() {
    final PGSimpleDataSource dataSource = new PGSimpleDataSource();
    dataSource.setUrl(DATABASE.getJdbcUrl());
    dataSource.setUser(DATABASE.getUsername());
    dataSource.setPassword(DATABASE.getPassword());
    return dataSource;
  }

  public void migrate(DataSource dataSource) {
    Flyway flyway =
        new Flyway(
            Flyway.configure().dataSource(dataSource).loggers("slf4j").locations("db/migrations"));
    flyway.migrate();
  }

  public Jdbi jdbi(Connection connection) {
    Jdbi jdbi =
        Jdbi.create(connection)
            .installPlugin(new PostgresPlugin())
            .installPlugin(new SqlObjectPlugin())
            .registerRowMapper(ConstructorMapper.factory(AccountView.class));
    return jdbi;
  }
}
