CREATE SCHEMA IF NOT EXISTS projects;

CREATE TABLE IF NOT EXISTS projects.projects
(
    id          SERIAL PRIMARY KEY,
    account_id  BIGINT NOT NULL,
    name        TEXT   NOT NULL,
    description TEXT   NOT NULL,
    UNIQUE (account_id, name),
    CONSTRAINT fk_account
        FOREIGN KEY (account_id)
            REFERENCES accounts.accounts (id)
);

CREATE TABLE IF NOT EXISTS projects.tasks
(
    account_id BIGINT NOT NULL,
    project_id BIGINT NOT NULL,
    task_id    BIGINT NOT NULL,
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

