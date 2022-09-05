create table if not exists users(
    id serial primary key,
    name text UNIQUE not null,
    password text not null
);

create table if not exists posts (
    id serial primary key,
    name text,
    description text,
    created timestamp without time zone not null default now(),
    author_id int references users(id)
);

create table if not exists comments(
    id serial primary key,
    body text not null,
    created timestamp without time zone not null default now(),
    author_id int references users(id),
    post_id int references posts(id)
);

