CREATE TABLE if not exists authorities(
    id serial primary key,
    authority text NOT NULL unique
);

create table if not exists users(
    id serial primary key,
    username text NOT NULL unique,
    password text NOT NULL,
    enabled boolean default true,
    authority_id int not null references authorities(id)
);

create table if not exists posts(
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

