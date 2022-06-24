create table cars
(
    id            serial PRIMARY KEY,
    car_brand     VARCHAR(128)    NOT NULL,
    year          INT             NOT NULL,
    engine_volume FLOAT           NOT NULL,
    body_type     VARCHAR         NOT NULL
);

create table computers
(
    id          serial PRIMARY KEY,
    brand       VARCHAR(128)    NOT NULL,
    ram         INT             NOT NULL,
    memory      INT             NOT NULL,
    memory_type VARCHAR(32)     NOT NULL,
    processor   VARCHAR(128)
);