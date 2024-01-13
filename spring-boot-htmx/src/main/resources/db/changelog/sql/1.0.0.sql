--liquibase formatted sql
--changeset g00glen00b:1-account

create table account (
    id          uuid not null primary key,
    username    varchar(32) not null,
    password    varchar(128) not null
);

--changeset g00glen00b:2-todoitem
create table todoitem_owner (
    id          uuid not null primary key
);

create table todoitem (
    id          uuid not null primary key,
    owner_id    uuid not null,
    task        varchar(256) not null,
    completed   bool not null
);

--changeset g00glen00b:3-events
CREATE TABLE IF NOT EXISTS event_publication
(
    id               UUID NOT NULL,
    listener_id      TEXT NOT NULL,
    event_type       TEXT NOT NULL,
    serialized_event TEXT NOT NULL,
    publication_date TIMESTAMP WITH TIME ZONE NOT NULL,
    completion_date  TIMESTAMP WITH TIME ZONE,
    PRIMARY KEY (id)
);

--changeset g00glen00b:4-sorting-todoitems
alter table todoitem add column created_at timestamp not null default current_timestamp;