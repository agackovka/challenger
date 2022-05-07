create table users
(
    id         varchar not null
        primary key,
    name       varchar not null,
    first_name varchar,
    last_name  varchar
);

alter table users
    owner to postgres;

create table users_challenges
(
    userid      varchar not null,
    challengeid varchar not null,
    createdat   timestamp,
    id          integer generated always as identity
        primary key
);

alter table users_challenges
    owner to postgres;

create table challenges
(
    id         varchar not null
        primary key,
    progress   integer not null,
    goal       integer not null,
    ownuserid  varchar not null,
    chatid     varchar not null,
    name       varchar not null,
    created_at timestamp,
    updated_at timestamp,
    type       varchar,
    state      varchar,
    buttons    varchar
);

alter table challenges
    owner to postgres;

create table submissions
(
    id          varchar not null
        primary key,
    challengeid varchar not null,
    value       integer not null,
    user_id     varchar not null
);

alter table submissions
    owner to postgres;

