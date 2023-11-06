package com.rds.observato.extentions;

import com.rds.observato.ObservatoConfiguration;
import io.dropwizard.core.ConfiguredBundle;
import io.dropwizard.core.setup.Bootstrap;

public class AuthBundle implements ConfiguredBundle<ObservatoConfiguration> {

  @Override
  public void initialize(Bootstrap<?> bootstrap) {
    //        ConfiguredBundle.super.initialize(bootstrap);
  }
}
