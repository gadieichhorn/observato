CREATE TABLE IF NOT EXISTS obs.assignments
(
    id          BIGSERIAL NOT NULL,
    revision    INT DEFAULT 0,
    account_id  BIGINT    NOT NULL REFERENCES obs.accounts (id),
    task_id     BIGINT    NOT NULL REFERENCES obs.tasks (id),
    resource_id BIGINT    NOT NULL REFERENCES obs.resources (id),
    start_time  TIMESTAMP NOT NULL,
    end_time    TIMESTAMP NOT NULL,
    UNIQUE (task_id)
);

CREATE INDEX IF NOT EXISTS assignments_account_id_resource_id_start_time_end_time_index
    on obs.assignments (account_id, resource_id, start_time, end_time);


