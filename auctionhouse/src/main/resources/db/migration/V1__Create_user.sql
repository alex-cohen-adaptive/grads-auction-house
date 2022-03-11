-- CREATE TYPE STATUS AS ENUM ('OPEN','CLOSED');

/*CREATE TYPE STATE AS ENUM ('PENDING','LOST','WIN');

CREATE TABLE organization
(
    org_id SERIAL PRIMARY KEY,
    name   VARCHAR(50) UNIQUE NOT NULL,
);*/
CREATE TABLE auctionuser
(
    id           SERIAL PRIMARY KEY,
    username     VARCHAR(50) UNIQUE    NOT NULL,
    password     VARCHAR(50)           NOT NULL,
    first_name   VARCHAR(50)           NOT NULL,
    last_name    VARCHAR(50)           NOT NULL,
    organization VARCHAR(50)           NOT NULL,
    is_admin     BOOLEAN DEFAULT FALSE NOT NULL,
    is_blocked   BOOLEAN DEFAULT FALSE NOT NULL
);

CREATE TABLE auction
(
    id            SERIAL PRIMARY KEY,
    symbol        VARCHAR(5) UNIQUE                      NOT NULL,
    quantity      BIGINT                                 NOT NULL,
    min_price     DOUBLE PRECISION                       NOT NULL,
    time_provider TIMESTAMP WITH TIME ZONE DEFAULT now() NOT NULL,
    status        VARCHAR(20)                            NOT NULL,
    owner         VARCHAR(50)                            NOT NULL REFERENCES auctionuser (username)
);

/*CREATE TABLE bid
(
    bid_id       SERIAL PRIMARY KEY,
    name         VARCHAR(50)      NOT NULL,
    quantity     BIGINT           NOT NULL,
    price        DOUBLE PRECISION NOT NULL,
--     state      STATE DEFAULT 'PENDING' NOT NULL,
    win_quantity BIGINT DEFAULT 0,
    username   SERIAL                  NOT NULL REFERENCES auctionUser (id),
    auction_id SERIAL                  NOT NULL REFERENCES auction (id)
);
*/

INSERT INTO auctionuser (username, password, first_name, last_name, organization, is_admin)
VALUES ('admin', 'adminpassword', 'andrew', 'ryan', 'rapture', true);