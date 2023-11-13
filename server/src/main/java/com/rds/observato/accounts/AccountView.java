package com.rds.observato.accounts;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

public record AccountView(
    @ColumnName("id") long id,
    @ColumnName("name") String name,
    @ColumnName("owner_id") long owner) {}
