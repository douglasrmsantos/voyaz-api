CREATE TABLE users
(
    id       BIGINT       NOT NULL AUTO_INCREMENT,
    login    VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
);
