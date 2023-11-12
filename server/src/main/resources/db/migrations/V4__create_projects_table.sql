CREATE TABLE IF NOT EXISTS obs.projects
(
    id          BIGSERIAL PRIMARY KEY,
    account_id  BIGINT NOT NULL REFERENCES obs.accounts (id),
    name        TEXT   NOT NULL,
    description TEXT   NOT NULL,
    UNIQUE (account_id, name)
);

CREATE TABLE IF NOT EXISTS obs.project_tasks
(
    account_id BIGINT NOT NULL REFERENCES obs.accounts (id),
    project_id BIGINT NOT NULL REFERENCES obs.projects (id),
    task_id    BIGINT NOT NULL REFERENCES obs.tasks (id),
    UNIQUE (account_id, task_id),
    UNIQUE (account_id, task_id, project_id)
)

