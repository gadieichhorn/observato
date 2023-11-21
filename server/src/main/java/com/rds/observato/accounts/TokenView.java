package com.rds.observato.accounts;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

import java.time.Instant;

public record TokenView(long id,
                        @ColumnName("user_id") long user,
                        @ColumnName("account_id") long account,
                        @ColumnName("token") String token,
                        @ColumnName("created_on")Instant createdOn){}

