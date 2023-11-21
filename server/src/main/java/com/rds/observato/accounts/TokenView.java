package com.rds.observato.accounts;

import java.time.Instant;
import org.jdbi.v3.core.mapper.reflect.ColumnName;

public record TokenView(
    @ColumnName("id") long id,
    @ColumnName("user_id") long user,
    @ColumnName("account_id") long account,
    @ColumnName("token") String token,
    @ColumnName("created_on") Instant createdOn) {}
