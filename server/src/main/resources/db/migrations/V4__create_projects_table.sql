-- CREATE SCHEMA IF NOT EXISTS prj;

CREATE TABLE IF NOT EXISTS projects
(
    id          BIGSERIAL PRIMARY KEY,
    account_id  BIGINT NOT NULL REFERENCES accounts (id),
    name        TEXT   NOT NULL,
    description TEXT   NOT NULL,
    UNIQUE (account_id, name)
);

CREATE TABLE IF NOT EXISTS project_tasks
(
    account_id BIGINT NOT NULL REFERENCES accounts (id),
    project_id BIGINT NOT NULL REFERENCES projects (id),
    task_id    BIGINT NOT NULL REFERENCES tasks (id)
--     UNIQUE (account_id, task_id),
--     UNIQUE (account_id, task_id, project_id)
)

