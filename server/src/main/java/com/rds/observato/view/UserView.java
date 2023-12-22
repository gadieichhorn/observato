package com.rds.observato.view;

import com.rds.observato.users.UserRecord;
import io.dropwizard.views.common.View;

public class UserView extends View {

  private final UserRecord user;

  public UserView(UserRecord user) {
    super("user.ftl");
    this.user = user;
  }

  public UserRecord getUser() {
    return user;
  }
}
