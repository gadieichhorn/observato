// package com.rds.observato.accounts;
//
// import com.rds.observato.api.persistence.Repository;
// import com.rds.observato.persistence.HashIdGenerator;
// import jakarta.ws.rs.*;
//
// public record AccountService(Repository repository) {
//
//  private static final HashIdGenerator idGenerator = HashIdGenerator.create();
//
//  public GetAccountResponse getOne(String key) {
//    long id = idGenerator.decode(key);
//    return repository
//        .accounts()
//        .findById(id)
//        .map(GetAccountResponse::from)
//  }
// }
