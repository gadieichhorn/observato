package com.rds.observato.accounts;

import com.rds.observato.auth.Roles;
import org.jdbi.v3.core.mapper.reflect.ColumnName;

public record UserAccountView(
    @ColumnName("user_id") long user,
    @ColumnName("account_id") long account,
    @ColumnName("role") Roles role) {}
