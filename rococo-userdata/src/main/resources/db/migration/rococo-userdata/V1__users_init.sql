create extension if not exists "pgcrypto";

create table if not exists "users"
(
    id          UUID  UNIQUE NOT NULL DEFAULT (gen_random_uuid()),
    username  varchar(50) unique not null,
    firstname varchar(255),
    lastname   varchar(255),
    avatar     varchar(25555),
    primary key (id)
);