package com.rds.observato.accounts;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

public record AccountRecord(
    @ColumnName("id") long id,
    @ColumnName("revision") int revision,
    @ColumnName("name") String name,
    @ColumnName("owner_id") long owner) {}
