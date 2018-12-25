-- insert into PERSON (first_name, last_name) values ('미선', '김');
-- insert into PERSON (first_name, last_name) values ('솔희', '이');
-- insert into PERSON (first_name, last_name) values ('석영', '김');

insert into users (username, password, enabled) values ('user', '{noop}password', true);
insert into users (username, password, enabled) values ('admin', '{bcrypt}$2a$10$wTlP9OtsXoTCVWbF11gn/uA3kjJ8Tj9gWMy0/P7hVNkwhvaYSowN2', true);

insert into authorities (username, authority) values ('user', 'ROLE_USER');
insert into authorities (username, authority) values ('admin', 'ROLE_ADMIN');
