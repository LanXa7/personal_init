CREATE TABLE IF NOT EXISTS rbac_policy
(
    id     INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    type varchar(1)  NOT NULL,
    v0     varchar(64) NOT NULL,
    v1     varchar(64) NOT NULL,
    v2     varchar(64) NULL,
    v3     varchar(64) NULL,
    v4     varchar(64) NULL,
    v5     varchar(64) NULL
);

INSERT INTO rbac_policy (type, v0, v1, v2)
VALUES ('p', 'alice', 'data1', 'read'),
       ('p', 'bob', 'data2', 'write'),
       ('p', 'admin', 'data1', 'read'),
       ('p', 'admin', 'data1', 'write'),
       ('g', 'alice', 'admin', NULL);