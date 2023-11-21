package com.rds.observato.extentions;

import com.rds.observato.ObservatoConfiguration;
import com.rds.observato.auth.AutorisationExceptionMapper;
import io.dropwizard.core.ConfiguredBundle;
import io.dropwizard.core.setup.Environment;

public class ExceptionsBundle implements ConfiguredBundle<ObservatoConfiguration> {

  @Override
  public void run(ObservatoConfiguration configuration, Environment environment) throws Exception {
    environment.jersey().register(new AutorisationExceptionMapper());
  }
}
