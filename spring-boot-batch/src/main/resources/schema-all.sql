DROP TABLE spring_batch.people IF EXISTS;

CREATE TABLE spring_batch.people  (
    person_id BIGINT IDENTITY NOT NULL PRIMARY KEY,
    first_name VARCHAR(20),
    last_name VARCHAR(20)
);