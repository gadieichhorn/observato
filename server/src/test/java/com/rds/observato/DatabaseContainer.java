package com.rds.observato;

import com.rds.observato.persistence.DatabaseConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

public class DatabaseContainer extends PostgreSQLContainer<DatabaseContainer> {

  public static DatabaseContainer create(DatabaseConfiguration configuration) {
    DatabaseContainer databaseContainer = new DatabaseContainer(configuration);
    databaseContainer.start();
    return databaseContainer;
  }

  private DatabaseContainer(DatabaseConfiguration configuration) {
    super(DockerImageName.parse("postgres:15-alpine"));
    withUsername(configuration.username());
    withPassword(configuration.password());
    withDatabaseName(configuration.database());
  }
}
