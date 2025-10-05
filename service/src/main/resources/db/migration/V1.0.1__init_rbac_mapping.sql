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