CREATE TABLE IF NOT EXISTS obs.assignments
(
    id          BIGSERIAL NOT NULL,
    account_id  BIGINT    NOT NULL REFERENCES obs.accounts (id),
    task_id     BIGINT    NOT NULL REFERENCES obs.tasks (id),
    resource_id BIGINT    NOT NULL REFERENCES obs.resources (id),
    start_time  TIMESTAMP NOT NULL,
    end_time    TIMESTAMP NOT NULL,
    UNIQUE (task_id)
);
