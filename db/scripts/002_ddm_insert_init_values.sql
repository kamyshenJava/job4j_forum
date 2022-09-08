insert into authorities (authority) values ('ROLE_USER'), ('ROLE_ADMIN');

insert into users (username, enabled, password, authority_id)
values ('admin', true, '$2a$10$DysaOxXQZ3DvdnCl6ebn3.K/zTXBu/UF7y//w40wmLMODaepHmP2i',
        (select id from authorities where authority = 'ROLE_ADMIN'));