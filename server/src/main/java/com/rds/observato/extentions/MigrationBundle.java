package com.rds.observato.extentions;

import com.rds.observato.ObservatoConfiguration;
import io.dropwizard.core.ConfiguredBundle;
import io.dropwizard.core.setup.Environment;
import org.flywaydb.core.Flyway;

public class MigrationBundle implements ConfiguredBundle<ObservatoConfiguration> {

  @Override
  public void run(ObservatoConfiguration configuration, Environment environment) throws Exception {
    new Flyway(
            Flyway.configure()
                .dataSource(
                    configuration.getDataSourceFactory().getUrl(),
                    configuration.getDataSourceFactory().getUser(),
                    configuration.getDataSourceFactory().getPassword())
                .loggers("slf4j")
                .locations("db/migrations"))
        .migrate();
  }
}
