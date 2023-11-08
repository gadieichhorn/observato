// package com.rds.observato.extentions;
//
// import com.rds.observato.ObservatoConfiguration;
// import com.rds.observato.auth.ObservatoAuthorizer;
// import com.rds.observato.auth.ObservatoBasicAuthenticator;
// import com.rds.observato.auth.User;
// import io.dropwizard.auth.AuthDynamicFeature;
// import io.dropwizard.auth.AuthValueFactoryProvider;
// import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
// import io.dropwizard.core.ConfiguredBundle;
// import io.dropwizard.core.setup.Environment;
// import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
//
// public class AuthBundle implements ConfiguredBundle<ObservatoConfiguration> {
//
//  @Override
//  public void run(ObservatoConfiguration configuration, Environment environment) {
//    environment
//        .jersey()
//        .register(
//            new AuthDynamicFeature(
//                new BasicCredentialAuthFilter.Builder<User>()
//                    .setAuthenticator(new ObservatoBasicAuthenticator())
//                    .setAuthorizer(new ObservatoAuthorizer())
//                    .setRealm("OBSERVATO")
//                    .buildAuthFilter()));
//    environment.jersey().register(RolesAllowedDynamicFeature.class);
//    environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));
//  }
// }
