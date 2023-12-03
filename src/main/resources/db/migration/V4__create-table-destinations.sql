CREATE TABLE destinations (
    id          BIGINT       NOT NULL AUTO_INCREMENT,
    photo      VARCHAR(100) NOT NULL,
    name        VARCHAR(100) NOT NULL,
    price       DECIMAL(10, 2) NOT NULL,
    PRIMARY KEY (id)
);

