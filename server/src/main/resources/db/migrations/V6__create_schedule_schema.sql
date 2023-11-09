CREATE SCHEMA IF NOT EXISTS schedule;

CREATE TABLE IF NOT EXISTS schedule.assignments
(
    account_id  BIGINT    NOT NULL,
    task_id     BIGINT    NOT NULL,
    resource_id BIGINT    NOT NULL,
    start_time  TIMESTAMP NOT NULL,
    end_time    TIMESTAMP NOT NULL,
    UNIQUE (account_id, task_id, resource_id),
    CONSTRAINT fk_account
        FOREIGN KEY (account_id)
            REFERENCES accounts.accounts (id),
    CONSTRAINT fk_task
        FOREIGN KEY (task_id)
            REFERENCES tasks.tasks (id),
    CONSTRAINT fk_resource
        FOREIGN KEY (resource_id)
            REFERENCES resources.resources (id)

);
