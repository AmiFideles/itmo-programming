CREATE TABLE IF NOT EXISTS users(
    id BIGSERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    login TEXT UNIQUE NOT NULL,
    password VARCHAR(128) NOT NULL,
    salt TEXT NOT NULL
);
CREATE TABLE IF NOT EXISTS location(
    id BIGSERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    x BIGINT,
    y DOUBLE NOT NULL,
    z DOUBLE
);
CREATE TABLE IF NOT EXISTS coordinate(
    id BIGSERIAL PRIMARY KEY,
    x BIGINT NOT NULL,
    y DOUBLE NOT NULL
);
CREATE TABLE IF NOT EXISTS person(
    id BIGSERIAL PRIMARY KEY,
    key BIGINT UNIQUE,
    name TEXT NOT NULL,
    height BIGINT,
    color TEXT,
    passportID TEXT NOT NULL,
    creation_date TIMESTAMP NOT NULL,
    birthday DATE NOT NULL ,
    coordinate_id BIGINT NOT NULL REFERENCES coordinate(id),
    location_id BIGINT NOT NULL REFERENCES location(id),
    user_id BIGINT  NOT NULL REFERENCES users(id)
);
