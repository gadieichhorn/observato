package com.rds.observato;

import com.rds.observato.api.persistence.Repository;
import com.rds.observato.extentions.AuthBundle;
import com.rds.observato.extentions.EnvironmentBundle;
import com.rds.observato.extentions.MigrationBundle;
import com.rds.observato.extentions.ObjectMapperBundle;
import com.rds.observato.persistence.RepositoryDao;
import com.rds.observato.server.controller.AccountController;
import com.rds.observato.server.controller.AccountsController;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jdbi3.JdbiFactory;
import java.time.ZoneOffset;
import java.util.TimeZone;
import org.jdbi.v3.core.Jdbi;

public class ObservatoApplication extends Application<ObservatoConfiguration> {

  public static void main(String[] args) throws Exception {
    TimeZone.setDefault(TimeZone.getTimeZone(ZoneOffset.UTC));
    new ObservatoApplication().run(args);
  }

  @Override
  public String getName() {
    return "observato";
  }

  @Override
  public void initialize(Bootstrap<ObservatoConfiguration> bootstrap) {
    super.initialize(bootstrap);

    bootstrap.addBundle(new AuthBundle());
    //    bootstrap.addBundle(new SwaggerBundle());
    //    bootstrap.addBundle(new ExceptionsBundle());
    //    bootstrap.addBundle(new PrometheusBundle());
    bootstrap.addBundle(new MigrationBundle());
    bootstrap.addBundle(new EnvironmentBundle());
    bootstrap.addBundle(new ObjectMapperBundle());
    bootstrap.addBundle(new AssetsBundle("/swagger", "/gateway/swagger", "index.html", "docs"));
  }

  @Override
  public void run(ObservatoConfiguration configuration, Environment environment) throws Exception {
    DataSourceFactory dataSourceFactory = configuration.getDataSourceFactory();

    Jdbi jdbi = new JdbiFactory().build(environment, dataSourceFactory, "observato");
    Repository repository = RepositoryDao.create(jdbi);

    environment.jersey().register(new AccountController(repository));
    environment.jersey().register(new AccountsController(repository));
  }
}
