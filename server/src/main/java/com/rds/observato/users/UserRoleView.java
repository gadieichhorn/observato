package com.rds.observato.users;

import com.rds.observato.auth.Role;
import org.jdbi.v3.core.mapper.reflect.ColumnName;

public record UserRoleView(
    @ColumnName("user_id") long user,
    @ColumnName("account_id") long account,
    @ColumnName("role") Role role) {}
