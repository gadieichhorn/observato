package com.rds.observato.auth;

import static org.assertj.core.api.Assertions.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class AuthServiceTest {

  AuthService service = AuthService.create();

  @Test
  void salt() {
    byte[] salt = service.salt();
    assertThat(salt).isNotEmpty();
  }

  @Test
  void saltSize() {
    byte[] salt = service.salt();
    assertThat(salt).hasSize(24);
  }

  @Test
  void saltNotSame() {
    byte[] salt1 = service.salt();
    byte[] salt2 = service.salt();
    //    assertThat(Arrays.compare(salt1, salt1)).isEqualTo(0);
    assertThat(Arrays.compare(salt1, salt2)).isNotEqualTo(0);
  }

  @Test
  void hash() throws NoSuchAlgorithmException, InvalidKeySpecException {
    byte[] salt = service.salt();
    byte[] hash = service.hash(salt, UUID.randomUUID().toString());
    assertThat(hash).hasSizeGreaterThan(24);
  }

  @Test
  void verifyShort() throws NoSuchAlgorithmException, InvalidKeySpecException {
    byte[] salt = service.salt();
    byte[] hash = service.hash(salt, "a");
    assertThat(service.verify(salt, hash, "a")).isTrue();
  }

  @Test
  void verifyLong() throws NoSuchAlgorithmException, InvalidKeySpecException {
    String password = UUID.randomUUID().toString();
    byte[] salt = service.salt();
    byte[] hash = service.hash(salt, password);
    assertThat(service.verify(salt, hash, password)).isTrue();
  }

  @Test
  void verifyFailed() throws NoSuchAlgorithmException, InvalidKeySpecException {
    byte[] salt = service.salt();
    byte[] hash = service.hash(salt, "a");
    assertThat(service.verify(salt, hash, "aa")).isFalse();
  }
}
