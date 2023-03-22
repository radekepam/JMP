--liquibase formatted sql
--changeset rkraj:1

CREATE TABLE APP_USER (
	id long auto_increment primary key,
    username VARCHAR(15) NOT NULL,
    email VARCHAR(200) NOT NULL,
	password VARCHAR(256) NOT NULL,
	is_enabled BOOLEAN NOT NULL DEFAULT 'false',
	user_role ENUM('ADMIN', 'USER')
);