package com.rds.observato.extentions;

import com.rds.observato.ObservatoConfiguration;
import com.rds.observato.auth.AutorisationExceptionMapper;
import com.rds.observato.jdbi.JdbiExceptionMapper;
import com.rds.observato.jdbi.SqlExceptionMapper;
import com.rds.observato.validation.ValidationExceptionMapper;
import io.dropwizard.core.ConfiguredBundle;
import io.dropwizard.core.setup.Environment;

public class ExceptionsBundle implements ConfiguredBundle<ObservatoConfiguration> {

  @Override
  public void run(ObservatoConfiguration configuration, Environment environment) throws Exception {
    environment.jersey().register(new SqlExceptionMapper());
    environment.jersey().register(new JdbiExceptionMapper());
    environment.jersey().register(new ValidationExceptionMapper());
    environment.jersey().register(new AutorisationExceptionMapper());
  }
}
