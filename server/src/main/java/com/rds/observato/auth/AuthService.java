package com.rds.observato.auth;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public record AuthService(SecureRandom random) {

  public static final int HASH_SIZE = 24;
  public static final int ITERATION_COUNT = 65536;
  public static final int KEY_LENGTH = 254;

  public static AuthService create() {
    return new AuthService(new SecureRandom());
  }

  public byte[] salt() {
    byte[] salt = new byte[HASH_SIZE];
    random.nextBytes(salt);
    return salt;
  }

  public byte[] hash(byte[] salt, String password)
      throws NoSuchAlgorithmException, InvalidKeySpecException {
    KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATION_COUNT, KEY_LENGTH);
    return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1").generateSecret(spec).getEncoded();
  }

  public Boolean verify(byte[] salt, byte[] hash, String password)
      throws InvalidKeySpecException, NoSuchAlgorithmException {
    KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATION_COUNT, KEY_LENGTH);
    SecretKeyFactory pbkdf2WithHmacSHA1 = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
    byte[] encoded = pbkdf2WithHmacSHA1.generateSecret(spec).getEncoded();
    return Arrays.equals(encoded, hash);
  }
}
