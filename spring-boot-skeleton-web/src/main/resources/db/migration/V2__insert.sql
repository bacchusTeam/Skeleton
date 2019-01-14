-- insert into PERSON (first_name, last_name) values ('미선', '김');
-- insert into PERSON (first_name, last_name) values ('솔희', '이');
-- insert into PERSON (first_name, last_name) values ('석영', '김');

insert into users (username, password, enabled) values ('user', '{noop}password', true);
insert into users (username, password, enabled) values ('admin', '{bcrypt}$2a$10$wTlP9OtsXoTCVWbF11gn/uA3kjJ8Tj9gWMy0/P7hVNkwhvaYSowN2', true);

insert into authorities (username, authority) values ('user', 'ROLE_USER');
insert into authorities (username, authority) values ('admin', 'ROLE_ADMIN');

INSERT INTO `oauth_client_details`(
  `client_id`,
  `resource_ids`,
  `client_secret`,
  `scope`,
  `authorized_grant_types`,
  `web_server_redirect_uri`,
  `authorities`,
  `access_token_validity`,
  `refresh_token_validity`,
  `additional_information`,
  `autoapprove`
  )

  VALUES(
  'client',
  null,
  '{noop}password',
  'read_profile,read_posts',
  'authorization_code,implicit,password,client_credentials,refresh_token',
  'https://local.zzizily.com:8443/api/v1/user',
  null,
  3000,
  6000,
  null ,
  'false'
  );