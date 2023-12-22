package com.rds.observato.tasks;

import java.util.Map;
import org.jdbi.v3.core.mapper.reflect.ColumnName;
import org.jdbi.v3.json.Json;

public record TaskRecord(
    @ColumnName("id") long id,
    @ColumnName("revision") int revision,
    @ColumnName("account_id") long account,
    @ColumnName("name") String name,
    @ColumnName("description") String description,
    @ColumnName("skills") @Json Map<String, Object> skills) {}
