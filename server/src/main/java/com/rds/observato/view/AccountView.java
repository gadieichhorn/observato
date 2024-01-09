package com.rds.observato.view;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rds.observato.accounts.AccountRecord;
import io.dropwizard.views.common.View;

public class AccountView extends View {

  private final AccountRecord account;

  @JsonCreator
  public AccountView(@JsonProperty("account") AccountRecord account) {
    super("account.ftl");
    this.account = account;
  }

  public AccountRecord getAccount() {
    return account;
  }
}
