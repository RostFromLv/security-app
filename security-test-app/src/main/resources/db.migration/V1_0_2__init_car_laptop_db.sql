create table security_user
(
    id             serial       NOT NULL PRIMARY KEY,
    principal_name varchar(512) NOT NULL,
    user_id        INT UNIQUE   NOT NULL,
    auth_provider  varchar(128)
)