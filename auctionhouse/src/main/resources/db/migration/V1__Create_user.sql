CREATE TYPE STATE AS ENUM ('PENDING','LOST','WIN');

CREATE TABLE organization
(
    org_id SERIAL PRIMARY KEY,
    name   VARCHAR(50) UNIQUE NOT NULL,
);

CREATE TABLE auction
(
    auction_id SERIAL PRIMARY KEY,
    symbol     VARCHAR(5)       NOT NULL,
    quantity   BIGINT           NOT NULL,
--         OR MAYBE DECIMAL (10,2)
    price      DOUBLE PRECISION NOT NULL,

);

CREATE TABLE bid
(
    bid_id     SERIAL PRIMARY KEY,
    name       VARCHAR(50)      NOT NULL,
    quantity   BIGINT           NOT NULL,
--         OR MAYBE DECIMAL (10,2)
    price      DOUBLE PRECISION NOT NULL,
    state      STATE DEAFULT 'PENDING' NOT NULL,
    user_id    SERIAL           NOT NULL REFERENCES user (user_id),
    auction_id SERIAL           NOT NULL REFERENCES auction (auction_id)
);

CREATE TABLE user
(
    user_id      SERIAL PRIMARY KEY,
    username     VARCHAR(50) UNIQUE NOT NULL,
    password     VARCHAR(50)        NOT NULL,
    first_name   VARCHAR(50)        NOT NULL,
    last_name    VARCHAR(50)        NOT NULL,
    organization VARCHAR(50)        NOT NULL,
    is_admin     BOOLEAN            NOT NULL,
    is_blocked   BOOLEAN            NOT NULL,
--     todo this comes later..
--  todo this comes later...
--     org_id SERIAL NOT NULL REFERENCES organization(org_id, name)
);