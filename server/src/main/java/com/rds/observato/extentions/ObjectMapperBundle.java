package com.rds.observato.extentions;

import com.rds.observato.ObservatoConfiguration;
import io.dropwizard.core.ConfiguredBundle;
import io.dropwizard.core.setup.Bootstrap;

public class ObjectMapperBundle implements ConfiguredBundle<ObservatoConfiguration> {

  @Override
  public void initialize(Bootstrap<?> bootstrap) {
    bootstrap.setObjectMapper(Mapper.create());
  }
}
