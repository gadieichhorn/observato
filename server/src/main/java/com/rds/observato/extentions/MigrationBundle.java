package com.rds.observato.extentions;

import com.rds.observato.ObservatoConfiguration;
import io.dropwizard.core.ConfiguredBundle;
import io.dropwizard.core.setup.Environment;
import org.flywaydb.core.Flyway;

public class MigrationBundle implements ConfiguredBundle<ObservatoConfiguration> {

  @Override
  public void run(ObservatoConfiguration configuration, Environment environment) throws Exception {
    Flyway flyway =
        new Flyway(
            Flyway.configure()
                .dataSource(
                    configuration.getDataSourceFactory().build(environment.metrics(), "observato"))
                .loggers("slf4j")
                .locations("db/migrations"));
    flyway.migrate();
  }
}
