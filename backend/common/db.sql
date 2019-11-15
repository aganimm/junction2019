create table user_profile
(
    user_id        integer primary key,
    mini_app_token varchar(512) not null,
    access_token   varchar(512) not null
);