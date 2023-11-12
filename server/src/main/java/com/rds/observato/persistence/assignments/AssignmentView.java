package com.rds.observato.persistence.assignments;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

public record AssignmentView(
    @ColumnName("id") long id,
    @ColumnName("account_id") long account,
    @ColumnName("task_id") long task,
    @ColumnName("resource_id") long resource) {}
