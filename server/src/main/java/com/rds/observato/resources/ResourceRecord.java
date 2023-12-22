package com.rds.observato.resources;

import java.util.Map;
import org.jdbi.v3.core.mapper.reflect.ColumnName;
import org.jdbi.v3.json.Json;

public record ResourceRecord(
    @ColumnName("id") long id,
    @ColumnName("revision") int revision,
    @ColumnName("account_id") long account,
    @ColumnName("name") String name,
    @ColumnName("skills") @Json Map<String, Object> skills) {}
