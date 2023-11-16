CREATE TABLE testimonials
(
    id                    BIGINT       NOT NULL AUTO_INCREMENT,
    photo                 VARCHAR(100) NOT NULL,
    text_testimony        VARCHAR(100) NOT NULL,
    name_person_testimony VARCHAR(11)  NOT NULL,
    PRIMARY KEY (id)
);
