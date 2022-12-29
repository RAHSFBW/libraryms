DROP TABLE IF EXISTS app_user;
CREATE TABLE IF NOT EXISTS app_user
(
    id          BIGINT PRIMARY KEY,
    user_name   VARCHAR(64) not null,
    password    VARCHAR(32),
    sex         VARCHAR(2),
    email       VARCHAR(64),
    phone       VARCHAR(16),
    user_role   VARCHAR(16),
    is_delete   INTEGER default 0,
    create_time timestamp,
    update_time timestamp
);
DROP TABLE IF EXISTS book;
CREATE TABLE IF NOT EXISTS book
(
    id          BIGINT PRIMARY KEY,
    name        VARCHAR(64) not null,
    author      VARCHAR(32),
    publisher   VARCHAR(64),
    type        INT     DEFAULT 99,
    total       INT     DEFAULT 0,
    num         INT     DEFAULT 0 check (num >= 0),
    is_delete   INTEGER default 0,
    create_time timestamp,
    update_time timestamp
);
DROP TABLE IF EXISTS borrow;
CREATE TABLE IF NOT EXISTS borrow
(
    id          BIGINT PRIMARY KEY,
    book_id     VARCHAR(256),
    user_name   VARCHAR(64),
    borrow_time  timestamp,
    borrow_days  INT,
    return_time  timestamp,
    status      INT,
    create_time timestamp,
    update_time timestamp
);
DROP TABLE IF EXISTS dict;
CREATE TABLE IF NOT EXISTS dict
(
    id          BIGINT PRIMARY KEY,
    dict_name   VARCHAR(256) ,
    dict_code   VARCHAR(64) ,
    item_key         INT,
    item_value       varchar(256),
    is_delete   INTEGER default 0,
    create_time timestamp,
    update_time timestamp
);