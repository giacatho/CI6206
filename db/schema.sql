CREATE DATABASE ci6206;

USE ci6206;

DROP TABLE IF EXISTS t_users;
CREATE TABLE t_users (
    c_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    c_username VARCHAR(20) NOT NULL,
    c_firstname VARCHAR(255) NOT NULL,
    c_lastname VARCHAR(255) NOT NULL,
    PRIMARY KEY(c_id),
    UNIQUE(c_username)
);

ALTER TABLE t_users 
    ADD c_password VARCHAR(20) NOT NULL;