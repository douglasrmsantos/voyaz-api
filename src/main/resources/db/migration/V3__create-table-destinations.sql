CREATE TABLE destinations
(
    id               BIGINT AUTO_INCREMENT NOT NULL,
    photo1           VARCHAR(100)          NOT NULL,
    photo2           VARCHAR(100)          NOT NULL,
    name             VARCHAR(100)          NOT NULL,
    price            DECIMAL(10, 2)        NOT NULL,
    goal             VARCHAR(160)          NOT NULL,
    descriptive_text TEXT                  NOT NULL,
    PRIMARY KEY (id)
);

