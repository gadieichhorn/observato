package com.rds.observato.persistence;

import org.hashids.Hashids;

public record HashIdGenerator(Hashids hashids) {

  private static final String DEFAULT_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

  public static HashIdGenerator create() {
    return create("observato");
  }

  public static HashIdGenerator create(String salt) {
    return new HashIdGenerator(new Hashids(salt, 32, DEFAULT_ALPHABET));
  }

  public long decode(String id) {
    return hashids.decode(id)[0];
  }

  public String encode(long id) {
    return hashids.encode(id);
  }
}
