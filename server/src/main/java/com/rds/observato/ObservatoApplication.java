package com.rds.observato;

import com.rds.observato.api.persistence.Repository;
import com.rds.observato.auth.AuthService;
import com.rds.observato.auth.ObservatoAuthorizer;
import com.rds.observato.auth.ObservatoBasicAuthenticator;
import com.rds.observato.auth.User;
import com.rds.observato.controller.account.AccountController;
import com.rds.observato.controller.account.AccountsController;
import com.rds.observato.controller.users.UserController;
import com.rds.observato.controller.users.UsersController;
import com.rds.observato.extentions.*;
import com.rds.observato.persistence.RepositoryDao;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jdbi3.JdbiFactory;
import java.time.ZoneOffset;
import java.util.TimeZone;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

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

    //    bootstrap.addBundle(new AuthBundle());
    //    bootstrap.addBundle(new SwaggerBundle());
    //    bootstrap.addBundle(new ExceptionsBundle());
    //    bootstrap.addBundle(new PrometheusBundle());
    bootstrap.addBundle(new MigrationBundle());
    bootstrap.addBundle(new EnvironmentBundle());
    bootstrap.addBundle(new ObjectMapperBundle());
    bootstrap.addBundle(new AssetsBundle("/swagger", "/gateway/swagger", "index.html", "docs"));
  }

  @Override
  public void run(ObservatoConfiguration configuration, Environment environment) {
    DataSourceFactory dataSourceFactory = configuration.getDataSourceFactory();

    Repository repository =
        RepositoryDao.create(new JdbiFactory().build(environment, dataSourceFactory, "observato"));
    AuthService auth = AuthService.create();

    //    MetricRegistry metrics = environment.metrics();
    //    CachingAuthenticator<BasicCredentials, User> cachingAuthenticator =
    //        new CachingAuthenticator<>(
    //            metricRegistry, simpleAuthenticator,
    // configuration.getAuthenticationCachePolicy());

    environment
        .jersey()
        .register(
            new AuthDynamicFeature(
                new BasicCredentialAuthFilter.Builder<User>()
                    .setAuthenticator(new ObservatoBasicAuthenticator(repository, auth))
                    .setAuthorizer(new ObservatoAuthorizer())
                    .setRealm("OBSERVATO")
                    .buildAuthFilter()));

    environment.jersey().register(RolesAllowedDynamicFeature.class);
    environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));

    environment.jersey().register(new UserController(repository));
    environment.jersey().register(new UsersController(repository, auth));
    environment.jersey().register(new AccountController(repository));
    environment.jersey().register(new AccountsController(repository));

    environment.admin().addTask(new GenerateDemoDataTask(repository, auth));
  }
}
