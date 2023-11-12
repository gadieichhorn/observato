package com.rds.observato.persistence.users;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

public record LoginView(
    @ColumnName("id") long id,
    @ColumnName("name") String name,
    @ColumnName("salt") byte[] salt,
    @ColumnName("secret") byte[] secret) {}
