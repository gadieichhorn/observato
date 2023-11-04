package com.rds.observato.schema;

import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class HashIdGeneratorTest {

  @Test
  void encode() {
    HashIdGenerator agent = HashIdGenerator.create("agent");
    String encode = agent.encode(1000);
    long decode = agent.decode(encode);
    System.out.println(encode);
    Assertions.assertThat(decode).isEqualTo(1000);
  }
}
