select id, revision, name, salt, secret
from obs.users
where name = :name
