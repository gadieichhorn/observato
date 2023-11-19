package com.rds.observato.accounts;

public record GetAccountResponse(long id, int revision, String name, long owner) {

  public static GetAccountResponse from(AccountView view) {
    return new GetAccountResponse(view.id(), view.revision(), view.name(), view.owner());
  }
}
