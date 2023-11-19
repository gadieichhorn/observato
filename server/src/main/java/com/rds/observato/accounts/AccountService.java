package com.rds.observato.accounts;

import com.rds.observato.api.persistence.Repository;
import com.rds.observato.persistence.HashIdGenerator;
import jakarta.ws.rs.*;
import java.util.Optional;

public record AccountService(Repository repository) {

  public static AccountConverter converter = new AccountConverter();
  private static final HashIdGenerator idGenerator = HashIdGenerator.create();

  public Optional<GetAccountResponse> getOne(String key) {
    long id = idGenerator.decode(key);
    return repository.accounts().findById(id).map(converter::convert);
  }
}
