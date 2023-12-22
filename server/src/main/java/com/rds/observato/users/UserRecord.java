package com.rds.observato.users;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

public record UserRecord(
    @ColumnName("id") long id,
    @ColumnName("revision") int revision,
    @ColumnName("name") String name) {}
