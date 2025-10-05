CREATE TABLE IF NOT EXISTS rbac_policy
(
    id   INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    type varchar(1)  NOT NULL,
    v0   varchar(64) NOT NULL,
    v1   varchar(64) NOT NULL,
    v2   varchar(64) NULL,
    v3   varchar(64) NULL,
    v4   varchar(64) NULL,
    v5   varchar(64) NULL
);


CREATE TABLE IF NOT EXISTS "user"
(
    id           INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    username     VARCHAR(32) NOT NULL,
    gender       VARCHAR(8)  NOT NULL,
    created_time TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    reviser_id   INTEGER     NULL,
    reviser_name VARCHAR(32) NULL,
    revised_time TIMESTAMPTZ NULL
);

CREATE TABLE IF NOT EXISTS account
(
    id           INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    account_name VARCHAR(32) NOT NULL,
    phone        VARCHAR(16) NOT NULL,
    email        VARCHAR(32) NOT NULL,
    password     VARCHAR(16) NOT NULL,
    created_time TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    reviser_id   INTEGER     NULL,
    reviser_name VARCHAR(32) NULL,
    revised_time TIMESTAMPTZ NULL,
    user_id      INTEGER     NOT NULL REFERENCES "user" (id),
    UNIQUE (phone),
    UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS role
(
    id           INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    code         VARCHAR(32) NOT NULL,
    name         VARCHAR(32) NOT NULL,
    creator_id   INTEGER     NOT NULL,
    creator_name VARCHAR(32) NOT NULL,
    created_time TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    reviser_id   INTEGER     NULL,
    reviser_name VARCHAR(32) NULL,
    revised_time TIMESTAMPTZ NULL,
    UNIQUE (code)
);

CREATE TABLE IF NOT EXISTS permission
(
    id           INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    code         VARCHAR(32) NOT NULL,
    name         VARCHAR(32) NOT NULL,
    creator_id   INTEGER     NOT NULL,
    creator_name VARCHAR(32) NOT NULL,
    created_time TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    reviser_id   INTEGER     NULL,
    reviser_name VARCHAR(32) NULL,
    revised_time TIMESTAMPTZ NULL,
    UNIQUE (code)
);

CREATE TABLE IF NOT EXISTS account_role_mapping
(
    account_id INTEGER NOT NULL REFERENCES account (id),
    role_id    INTEGER NOT NULL REFERENCES role (id),
    PRIMARY KEY (account_id, role_id)
    );

CREATE TABLE IF NOT EXISTS role_permission_mapping
(
    role_id       INTEGER NOT NULL REFERENCES role (id),
    permission_id INTEGER NOT NULL REFERENCES permission (id),
    PRIMARY KEY (role_id, permission_id)
);

INSERT INTO rbac_policy (type, v0, v1, v2)
VALUES ('p', 'alice', 'data1', 'read'),
       ('p', 'bob', 'data2', 'write'),
       ('p', 'admin', 'data1', 'read'),
       ('p', 'admin', 'data1', 'write'),
       ('g', 'alice', 'admin', NULL);
