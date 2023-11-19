package com.rds.observato.accounts;

import com.rds.observato.json.ResponseMapper;

public class AccountConverter implements ResponseMapper<AccountView, GetAccountResponse> {

  @Override
  public GetAccountResponse convert(AccountView view) {
    return new GetAccountResponse(view.id(), view.revision(), view.name(), view.owner());
  }
}
