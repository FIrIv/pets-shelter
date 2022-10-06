-- liquibase formatted sql

-- changeset s.yukov:1
create table if not exists information_message
(
    id   serial primary key,
    name text not null,
    text text not null
);

create table if not exists pet
(
    id   serial primary key,
    name text not null
);

create table if not exists pet_report
(
    id           serial primary key,
    petId        serial unique,
    dateOfReport date,
    textOfReport text not null,
    photoLink    text not null
);

create table if not exists users
(
    id               serial primary key,
    chatId           serial,
    name             text not null,
    phone            text not null,
    isAdopted        bool default false,
    startDate        date,
    quantityTestDAys int  default 30,
    pet_id           serial unique
);

create table if not exists volunteer
(
    id               serial primary key,
    chatId           serial,
    name             text not null
);