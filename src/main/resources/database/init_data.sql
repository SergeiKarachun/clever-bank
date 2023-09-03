CREATE TABLE IF NOT EXISTS users
(
    username VARCHAR PRIMARY KEY,
    password VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS users_info
(
    id          BIGSERIAL PRIMARY KEY,
    username    VARCHAR NOT NULL,
    first_name   VARCHAR NOT NULL,
    last_name    VARCHAR NOT NULL,
    phone_number VARCHAR NOT NULL,
    email       VARCHAR NOT NULL UNIQUE,
    FOREIGN KEY (username) REFERENCES users (username)
);

CREATE TABLE IF NOT EXISTS currencies
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR
);

CREATE TABLE IF NOT EXISTS bank
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS user_bank_account
(
    id                BIGSERIAL PRIMARY KEY,
    username          VARCHAR NOT NULL,
    bank_account_number BIGINT NOT NULL UNIQUE,
    balance           DECIMAL,
    currency_id        INT     NOT NULL,
    bank_id            INT     NOT NULL,
    created_at              DATE    NOT NULL DEFAULT CURRENT_DATE,
    FOREIGN KEY (username) REFERENCES users (username),
    FOREIGN KEY (currency_id) REFERENCES currencies (id),
    FOREIGN KEY (bank_id) REFERENCES bank (id)
);

CREATE TABLE IF NOT EXISTS transaction_types
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS transaction
(
    id              BIGSERIAL PRIMARY KEY,
    created_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    transaction_type INT     NOT NULL,
    from_bank_id      INT,
    to_bank_id        INT,
    payment_sender_account BIGINT,
    payment_receiver_account   BIGINT  NOT NULL,
    amount          DECIMAL NOT NULL,
    FOREIGN KEY (payment_sender_account) REFERENCES user_bank_account(bank_account_number),
    FOREIGN KEY (payment_receiver_account) REFERENCES transaction_types(id)
);


create procedure getUserPassword(p_username varchar)
    language plpgsql
    as $$
begin
    select password from users WHERE username = p_username;
end;$$

create procedure updateUserPassword(p_password varchar, p_username varchar)
    language plpgsql
    as $$
begin
    update users set password = p_password where username = p_username;
end;$$


