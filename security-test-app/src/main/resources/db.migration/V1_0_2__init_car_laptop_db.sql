create table security_user(
    id serial NOT NULL PRIMARY KEY,
    user_id INT UNIQUE NOT NULL ,
    provider_code varchar(512)
)