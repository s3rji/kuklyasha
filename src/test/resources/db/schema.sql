DROP TABLE IF EXISTS USER_ROLES;
DROP TABLE IF EXISTS USERS;
DROP TABLE IF EXISTS DOLL;

CREATE TABLE DOLL
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR UNIQUE          NOT NULL,
    description VARCHAR                 NOT NULL,
    price       NUMERIC(8, 2)           NOT NULL,
    image       VARCHAR                 NOT NULL,
    created     TIMESTAMP DEFAULT now() NOT NULL
);

CREATE TABLE USERS
(
    id       SERIAL PRIMARY KEY,
    name     VARCHAR                 NOT NULL,
    email    VARCHAR UNIQUE          NOT NULL,
    password VARCHAR                 NOT NULL,
    lastname VARCHAR,
    phone    VARCHAR UNIQUE,
    country  VARCHAR,
    city     VARCHAR,
    region   VARCHAR,
    street   VARCHAR,
    zipcode  VARCHAR,
    created  TIMESTAMP DEFAULT now() NOT NULL,
    enabled  BOOL      DEFAULT TRUE  NOT NULL
);

CREATE TABLE USER_ROLES
(
    user_id INTEGER NOT NULL,
    role    VARCHAR,
    CONSTRAINT user_roles_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);