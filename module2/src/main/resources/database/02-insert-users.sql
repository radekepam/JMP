--liquibase formatted sql
--changeset rkraj:1

insert into app_user( username, email, password, is_enabled, user_role) values ('User', 'User@app.pl', '$2a$10$Saj30KISV5b00Z2TmjC9ROVTR8Hiy6l/LLI5Ctv5F4j3TxOFhoF0S', true, 'USER');
insert into app_user( username, email, password, is_enabled, user_role) values ( 'User1', 'User1@app.pl', '$2a$10$Saj30KISV5b00Z2TmjC9ROVTR8Hiy6l/LLI5Ctv5F4j3TxOFhoF0S', true, 'USER');
insert into app_user( username, email, password, is_enabled, user_role) values ( 'Admin', 'Admin@app.pl', '$2a$10$Saj30KISV5b00Z2TmjC9ROVTR8Hiy6l/LLI5Ctv5F4j3TxOFhoF0S', true, 'ADMIN');
