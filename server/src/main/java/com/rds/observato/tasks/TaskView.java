package com.rds.observato.tasks;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

public record TaskView(
    @ColumnName("id") long id,
    @ColumnName("account_id") long account,
    @ColumnName("name") String name,
    @ColumnName("description") String description) {}
