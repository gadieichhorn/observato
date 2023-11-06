package com.rds.observato.extentions;

import com.rds.observato.ObservatoConfiguration;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.core.ConfiguredBundle;
import io.dropwizard.core.setup.Bootstrap;

public class EnvironmentBundle implements ConfiguredBundle<ObservatoConfiguration> {

  @Override
  public void initialize(Bootstrap<?> bootstrap) {
    bootstrap.setConfigurationSourceProvider(
        new SubstitutingSourceProvider(
            bootstrap.getConfigurationSourceProvider(), new EnvironmentVariableSubstitutor(true)));
  }
}
