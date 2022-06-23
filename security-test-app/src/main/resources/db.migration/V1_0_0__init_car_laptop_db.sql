create table car
(
    id            INT PRIMARY KEY NOT NULL,
    car_brand     VARCHAR(128)    NOT NULL,
    year          INT             NOT NULL,
    engine_volume FLOAT           NOT NULL,
    body_type     VARCHAR         NOT NULL
);

create table laptop
(
    id          INT PRIMARY KEY NOT NULL,
    brand       VARCHAR(128)    NOT NULL,
    ram         INT             NOT NULL,
    memory      INT             NOT NULL,
    memory_type VARCHAR(32)     NOT NULL,
    processor   VARCHAR(128)
);