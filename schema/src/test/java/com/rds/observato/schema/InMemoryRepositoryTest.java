package com.rds.observato.schema;

import com.rds.observato.api.persistence.Repository;
import java.util.HashMap;
import org.junit.jupiter.api.Test;

class InMemoryRepositoryTest {

  @Test
  void accountKey() {
    Repository repository = new InMemoryRepository(new HashMap<>());
    //    String key = repository.key(new Account(100, "adzqxerxq3", "test"));
    //    System.out.println(key);
  }
}
