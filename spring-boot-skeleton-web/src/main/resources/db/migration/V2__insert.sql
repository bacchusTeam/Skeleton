-- insert into PERSON (first_name, last_name) values ('미선', '김');
-- insert into PERSON (first_name, last_name) values ('솔희', '이');
-- insert into PERSON (first_name, last_name) values ('석영', '김');

insert into users (username, password, enabled) values ('user', '{noop}user', true);
insert into users (username, password, enabled) values ('ksy', '{noop}qwe123', true);

insert into authorities (username, authority) values ('user', 'ROLE_ADMIN');
insert into authorities (username, authority) values ('ksy', 'ROLE_USER');