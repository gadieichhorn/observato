package com.rds.observato;

import com.rds.observato.auth.AuthService;
import com.rds.observato.db.Repository;
import com.rds.observato.users.LoginView;
import com.rds.observato.users.UserView;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Optional;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.jdbi.v3.core.statement.UnableToExecuteStatementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UsersDaoTest extends DatabaseTestBase {

  private Repository repository;

  @BeforeEach
  void setUp() {
    repository = ObservatoRepository.create(jdbi());
  }

  @Test
  void create() {
    Assertions.assertThatCode(
            () ->
                repository
                    .users()
                    .create("account1", "hilla@observato.com".getBytes(), "secret".getBytes()))
        .doesNotThrowAnyException();
  }

  @Test
  void duplicate() {
    repository.users().create("account2", "gadi@observato.com".getBytes(), "secret".getBytes());
    Assertions.assertThatThrownBy(
            () ->
                repository
                    .users()
                    .create("account2", "gadi@observato.com".getBytes(), "secret".getBytes()))
        .isInstanceOf(UnableToExecuteStatementException.class);
  }

  @Test
  void createAndGet() throws InvalidKeySpecException, NoSuchAlgorithmException {
    long id =
        repository.users().create("account3", "gadi@observato.com".getBytes(), "secret".getBytes());
    Optional<UserView> user = repository.users().findById(id);
    Assertions.assertThat(user)
        .isPresent()
        .get()
        .isInstanceOf(UserView.class)
        .hasFieldOrPropertyWithValue("name", "account3");
  }

  @Test
  void login() {
    long id = repository.users().create("user123123", "salt".getBytes(), "password".getBytes());
    Optional<LoginView> login = repository.users().findByName("user123123");

    Assertions.assertThat(login)
        .isPresent()
        .get()
        .isInstanceOf(LoginView.class)
        .hasFieldOrPropertyWithValue("id", id)
        .hasFieldOrPropertyWithValue("name", "user123123");
  }

  @Test
  void loginNotFound() {
    Optional<LoginView> login = repository.users().findByName("4352452d45234523");
    Assertions.assertThat(login).isEmpty();
  }

  @Test
  void loginVerify() throws InvalidKeySpecException, NoSuchAlgorithmException {
    AuthService auth = AuthService.create();
    String password = UUID.randomUUID().toString();
    byte[] salt = auth.salt();
    repository.users().create("user45234532", salt, auth.hash(salt, password));

    LoginView view = repository.users().findByName("user45234532").get();
    Assertions.assertThat(auth.verify(view.salt(), view.secret(), password)).isTrue();
  }
}
