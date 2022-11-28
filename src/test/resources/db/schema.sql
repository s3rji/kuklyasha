DROP TABLE IF EXISTS ORDER_DOLL;
DROP TABLE IF EXISTS ORDERS;
DROP TABLE IF EXISTS USER_ROLES;
DROP TABLE IF EXISTS USERS;
DROP TABLE IF EXISTS DOLL;

CREATE TABLE DOLL
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR UNIQUE          NOT NULL,
    description VARCHAR                 NOT NULL,
    price       NUMERIC(8, 2)           NOT NULL,
    quantity     INTEGER   DEFAULT 0     NOT NULL,
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

CREATE TABLE ORDERS
(
    id              SERIAL PRIMARY KEY,
    user_id         INTEGER                 NOT NULL,
    status          VARCHAR                 NOT NULL,
    status_modified TIMESTAMP DEFAULT now() NOT NULL,
    total           NUMERIC(10, 2)          NOT NULL,
    created         TIMESTAMP DEFAULT now() NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE ORDER_DOLL
(
    order_id INTEGER NOT NULL,
    doll_id  INTEGER NOT NULL,
    PRIMARY KEY (order_id, doll_id),
    FOREIGN KEY (order_id) REFERENCES orders (id) ON DELETE CASCADE,
    FOREIGN KEY (doll_id) REFERENCES doll (id)
);