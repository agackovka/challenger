create table users
(
    id         varchar not null primary key,
    name       varchar not null,
    first_name varchar,
    last_name  varchar
);

create table users_challenges
(
    user_id      varchar not null,
    challenge_id varchar not null,
    created_at   timestamp,
    id          integer not null primary key
);

create table challenges
(
    id         varchar not null
        primary key,
    progress   integer not null,
    goal       integer not null,
    owner_user_id  varchar not null,
    chat_id     varchar not null,
    name       varchar not null,
    created_at timestamp,
    updated_at timestamp,
    type       varchar,
    state      varchar,
    buttons    varchar
);

create table submissions
(
    id          varchar not null
        primary key,
    challenge_id varchar not null,
    value       integer not null,
    user_id     varchar not null
);
