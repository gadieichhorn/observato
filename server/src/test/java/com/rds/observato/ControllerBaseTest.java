package com.rds.observato;

import com.rds.observato.auth.AutorisationExceptionMapper;
import com.rds.observato.auth.ObservatoAuthFilter;
import com.rds.observato.auth.ObservatoBasicAuthenticator;
import com.rds.observato.auth.User;
import com.rds.observato.jdbi.JdbiExceptionMapper;
import com.rds.observato.jdbi.SqlExceptionMapper;
import com.rds.observato.validation.ValidationExceptionMapper;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import java.util.function.Supplier;
import org.glassfish.jersey.test.grizzly.GrizzlyWebTestContainerFactory;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(DropwizardExtensionsSupport.class)
public class ControllerBaseTest extends DatabaseTestBase {

  protected long user = Fixtures.createUser(repository);
  protected String token = Fixtures.token(user);

  public ResourceExtension ext(Supplier<Object> controller) {
    return ResourceExtension.builder()
        .setTestContainerFactory(new GrizzlyWebTestContainerFactory())
        .addProvider(new ValidationExceptionMapper())
        .addProvider(new AutorisationExceptionMapper())
        .addProvider(new JdbiExceptionMapper())
        .addProvider(new SqlExceptionMapper())
        .addProvider(
            new AuthDynamicFeature(
                new ObservatoAuthFilter.Builder()
                    .setAuthenticator(new ObservatoBasicAuthenticator(repository))
                    .setRealm("OBSERVATO")
                    .buildAuthFilter()))
        .addProvider(new AuthValueFactoryProvider.Binder<>(User.class))
        .addProvider(controller)
        .build();
  }
}
