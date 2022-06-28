create table if not exists users(
    id         serial PRIMARY KEY,
    first_name VARCHAR(128) NOT NULL,
    last_name  VARCHAR(128) NOT NULL,
    email  VARCHAR(128)  UNIQUE NOT NULL,
    password   VARCHAR NOT NULL,
    CONSTRAINT UQ_users_lastName_firstName UNIQUE (first_name, last_name)
);