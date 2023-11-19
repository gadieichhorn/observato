package com.rds.observato.controller.login;

import com.rds.observato.DatabaseTestBase;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.glassfish.jersey.test.grizzly.GrizzlyWebTestContainerFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(DropwizardExtensionsSupport.class)
class LoginControllerTest extends DatabaseTestBase {

  public static final ResourceExtension RULE =
      ResourceExtension.builder()
          .addProvider(RolesAllowedDynamicFeature.class)
          .setTestContainerFactory(new GrizzlyWebTestContainerFactory())
          .addProvider(() -> new LoginController(repository()))
          .build();

  @BeforeAll
  static void setUp() {
    //        repository().users().create("")
  }

  @Test
  void get() {
    //        String credential = "Basic " +
    // Base64.getEncoder().encodeToString("internal:secret".getBytes());
    //        List<ConfigDataHistoryView> results =
    //                RULE.target("/gateway/config/history/c5-s5-p5")
    //                        .request()
    //                        .header(HttpHeaders.AUTHORIZATION, credential)
    //                        .get(new GenericType<>() {});
    //        assertThat(results.get(0).changeDescription()).isEqualTo("cd5");
  }
}
