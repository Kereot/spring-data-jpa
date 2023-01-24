CREATE TABLE users (
    id                    bigserial,
    username              varchar(30) not null unique,
    password              varchar(80) not null,
    email                 varchar(50) unique,
    primary key (id)
);

CREATE TABLE privileges (
    id                    serial,
    name                  varchar(50) not null,
    primary key (id)
);

CREATE TABLE users_privileges (
    user_id               bigint not null,
    privilege_id          int not null,
    primary key (user_id, privilege_id),
    foreign key (user_id) references users (id),
    foreign key (privilege_id) references privileges (id)
);

insert into privileges (name) values ('READ_ONLY'), ('READ_AND_WRITE');

INSERT INTO users (username, password, email) values
    ('user', '$2a$10$9Feu10NvX5ee9.LIzEWyMOWbShX9ScOLxdBwqnSnjLKRtPO.adR5e', 'user@gmail.com'),
    ('admin', '$2a$10$9Feu10NvX5ee9.LIzEWyMOWbShX9ScOLxdBwqnSnjLKRtPO.adR5e', 'admin@gmail.com');

INSERT INTO users_privileges (user_id, privilege_id) values
    (1, 1),
    (2, 1),
    (2, 2);