CREATE TABLE IF NOT EXISTS obs.skills
(
    id          BIGSERIAL PRIMARY KEY,
    revision    INT DEFAULT 0,
    account_id  BIGINT NOT NULL REFERENCES obs.accounts (id),
    name        TEXT   NOT NULL,
    description TEXT   NOT NULL,
    UNIQUE (account_id, name)
);

-- CREATE TABLE IF NOT EXISTS obs.tasks_skills
-- (
--     account_id BIGINT NOT NULL REFERENCES obs.accounts (id),
--     task_id    BIGINT NOT NULL REFERENCES obs.tasks (id),
--     skill_id   BIGINT NOT NULL REFERENCES obs.skills (id),
--     level      INT DEFAULT 0,
--     UNIQUE (account_id, task_id, skill_id)
-- );
--
-- CREATE TABLE IF NOT EXISTS obs.resource_skills
-- (
--     account_id  BIGINT NOT NULL REFERENCES obs.accounts (id),
--     resource_id BIGINT NOT NULL REFERENCES obs.resources (id),
--     skill_id    BIGINT NOT NULL REFERENCES obs.skills (id),
--     level       INT DEFAULT 0,
--     UNIQUE (account_id, resource_id, skill_id)
-- );
