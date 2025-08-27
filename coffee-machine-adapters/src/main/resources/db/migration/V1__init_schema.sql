-- V1: initial schema for coffee-machine simulator (H2)

create table if not exists recipe (
    id            uuid default random_uuid() primary key,
    name          varchar(50) not null unique,
    water         double not null,
    beans         double not null,
    temperature   double not null,
    grind         varchar(50) not null,
    brew_seconds  double not null
    );

create table if not exists coffee_machine (
    id                      uuid default random_uuid() primary key,
    version                 bigint,
    model                   varchar(255) not null,
    water_capacity          double not null,
    water_current           double not null,
    beans_capacity          double not null,
    beans_current           double not null,
    waste_capacity_pucks    int not null,
    waste_current_pucks     int not null,
    powered_on              boolean not null,
    is_brewing              boolean not null
);

create index if not exists idx_recipe_name on recipe(name);
