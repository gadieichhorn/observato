package com.rds.observato.resources;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

public record ResourceView(
    @ColumnName("id") long id,
    @ColumnName("revision") int revision,
    @ColumnName("account_id") long account,
    @ColumnName("name") String name) {}
