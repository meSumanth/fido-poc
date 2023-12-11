CREATE TABLE QUOTES(
   id INTEGER PRIMARY KEY,
   quote VARCHAR(1024),
   author VARCHAR(256)
);

INSERT INTO quotes (id,quote,author) VALUES (1,'Never, never, never give up','Winston Churchill');
INSERT INTO quotes (id,quote,author) VALUES (2,'While there''s life, there''s hope','Marcus Tullius Cicero');
INSERT INTO quotes (id,quote,author) VALUES (3,'Failure is success in progress','Anonymous');
INSERT INTO quotes (id,quote,author) VALUES (4,'Success demands singleness of purpose','Vincent Lombardi');
INSERT INTO quotes (id,quote,author) VALUES (5,'The shortest answer is doing','Lord Herbert');

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
