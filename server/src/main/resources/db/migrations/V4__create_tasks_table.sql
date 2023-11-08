CREATE SCHEMA IF NOT EXISTS tasks;

CREATE TABLE IF NOT EXISTS tasks.tasks
(
    id          SERIAL PRIMARY KEY,
    account_id  BIGINT NOT NULL,
    key         TEXT   NOT NULL,
    description TEXT   NOT NULL,
    UNIQUE (account_id, key),
    CONSTRAINT fk_account
        FOREIGN KEY (account_id)
            REFERENCES accounts.accounts (id)
);

CREATE TABLE IF NOT EXISTS tasks.project
(
    account_id BIGINT NOT NULL,
    task_id    BIGINT NOT NULL,
    project_id BIGINT NOT NULL,
    UNIQUE (account_id, task_id, project_id),
    CONSTRAINT fk_account
        FOREIGN KEY (account_id)
            REFERENCES accounts.accounts (id),
    CONSTRAINT fk_task
        FOREIGN KEY (task_id)
            REFERENCES tasks.tasks (id),
    CONSTRAINT fk_project
        FOREIGN KEY (project_id)
            REFERENCES projects.projects (id)
)

