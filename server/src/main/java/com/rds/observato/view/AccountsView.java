package com.rds.observato.view;

import com.rds.observato.accounts.UserAccountView;
import io.dropwizard.views.common.View;
import java.util.Set;

public class AccountsView extends View {

  private final Set<UserAccountView> accounts;

  public AccountsView(Set<UserAccountView> accounts) {
    super("accounts.ftl");
    this.accounts = accounts;
  }

  public Set<UserAccountView> getAccounts() {
    return accounts;
  }
}
