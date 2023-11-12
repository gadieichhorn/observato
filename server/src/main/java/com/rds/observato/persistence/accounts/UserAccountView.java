package com.rds.observato.persistence.accounts;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

public record UserAccountView(
    @ColumnName("user_id") long user,
    @ColumnName("account_id") long account,
    @ColumnName("role") String role) {}
