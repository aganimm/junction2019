create table user_profile
(
    user_id        integer primary key,
    mini_app_token varchar(512) not null,
    access_token   varchar(512) not null
);

CREATE SEQUENCE list_id START 1;

create table list
(
    list_id    integer primary key,
    user_id    integer      not null,
    title      varchar(256) not null,
    list_type  varchar(64)  not null,
    is_deleted boolean default false
);

create table list_id_to_product
(
    list_id     numeric   not null,
    product_id  numeric   not null,
    last_update TIMESTAMP not null default Now(),
    is_deleted  boolean   not null default false
)