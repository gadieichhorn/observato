package com.rds.observato.view;

import com.rds.observato.accounts.AccountRecord;
import io.dropwizard.views.common.View;

public class AccountView extends View {

  private final AccountRecord account;

  public AccountView(AccountRecord account) {
    super("account.ftl");
    this.account = account;
  }

  public AccountRecord getAccount() {
    return account;
  }
}
