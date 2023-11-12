-- CREATE SCHEMA IF NOT EXISTS scd;

CREATE TABLE IF NOT EXISTS assignments
(
    account_id  BIGINT    NOT NULL REFERENCES accounts (id),
    task_id     BIGINT    NOT NULL REFERENCES tasks (id),
    resource_id BIGINT    NOT NULL REFERENCES resources (id),
    start_time  TIMESTAMP NOT NULL,
    end_time    TIMESTAMP NOT NULL,
    UNIQUE (account_id, task_id, resource_id)
);
