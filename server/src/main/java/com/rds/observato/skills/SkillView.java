package com.rds.observato.skills;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

public record SkillView(
    @ColumnName("id") long id,
    @ColumnName("revision") int revision,
    @ColumnName("account_id") long account,
    @ColumnName("name") String name,
    @ColumnName("description") String description) {}
