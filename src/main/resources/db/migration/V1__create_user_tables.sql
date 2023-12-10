CREATE TABLE USER_ACCOUNT
(
    id        UUID PRIMARY KEY,
    email     VARCHAR(1024) UNIQUE NOT NULL
);

CREATE TABLE FIDO_CRED_STORE
(
    id              VARCHAR(2048) PRIMARY KEY,
    public_key_cose TEXT NOT NULL,
    user_id         UUID NOT NULL,
    type            TEXT NOT NULL
);

CREATE TABLE LOGIN_DETAILS
(
    id               UUID PRIMARY KEY,
    start_request    TEXT,
    start_response   TEXT,
    assertion_request TEXT,
    assertion_result  TEXT,
    successful_login BOOLEAN
);
