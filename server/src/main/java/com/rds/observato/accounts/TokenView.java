package com.rds.observato.accounts;

import com.google.common.collect.ImmutableSet;
import com.rds.observato.auth.Role;
import org.jdbi.v3.core.mapper.reflect.ColumnName;

public record TokenView(
    @ColumnName("user_id") long user,
    @ColumnName("account_id") long account,
    @ColumnName("name") String username,
    @ColumnName("roles") ImmutableSet<Role> roles) {}
