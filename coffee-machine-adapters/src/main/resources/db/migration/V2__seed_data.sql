-- V2: seed initial recipes and one coffee machine snapshot

merge into recipe (id, name, water, beans, temperature, grind, brew_seconds)
    key(name)
    values ('2dd641d1-b628-46b8-b3db-54b2fb409af2', 'ESPRESSO', 30.0, 18.0, 93.0, 'FINE', 25);

merge into recipe (id, name, water, beans, temperature, grind, brew_seconds)
    key(name)
    values ('9b74c4a3-c989-447e-8018-2d5aade22ef8', 'RISTRETTO', 22.0, 16.0, 94.0, 'FINE', 20);

merge into recipe (id, name, water, beans, temperature, grind, brew_seconds)
    key(name)
    values ('80c62d5f-522d-457e-97ef-f0246a42514c', 'LUNGO', 60.0, 18.0, 92.0, 'MEDIUM', 35);

merge into recipe (id, name, water, beans, temperature, grind, brew_seconds)
    key(name)
    values ('0da3afe6-d794-49a7-9502-eae556c7af72', 'AMERICANO', 120.0, 18.0, 93.0, 'MEDIUM', 25);

merge into recipe (id, name, water, beans, temperature, grind, brew_seconds)
    key(name)
    values ('f3384644-a249-49fe-b104-349277015ea1', 'LONG_BLACK', 100.0, 18.0, 93.0, 'MEDIUM', 25);

merge into recipe (id, name, water, beans, temperature, grind, brew_seconds)
    key(name)
    values ('ea54c297-048a-45f9-be19-7566745d286f', 'LATTE', 35.0, 18.0, 93.0, 'MEDIUM', 30);

merge into recipe (id, name, water, beans, temperature, grind, brew_seconds)
    key(name)
    values ('9cff2ba3-0147-432c-b92d-6dba3fe290a4', 'CAPPUCCINO', 30.0, 18.0, 93.0, 'FINE', 25);

merge into recipe (id, name, water, beans, temperature, grind, brew_seconds)
    key(name)
    values ('67a94fb4-66ef-4e06-86a5-c330773cec69', 'FLAT_WHITE', 30.0, 18.0, 92.0, 'FINE', 25);

merge into recipe (id, name, water, beans, temperature, grind, brew_seconds)
    key(name)
    values ('9a0ce96f-e5b3-417a-90e3-520d176a2d52', 'DOUBLE_ESPRESSO', 60.0, 36.0, 94.0, 'FINE', 35);

merge into recipe (id, name, water, beans, temperature, grind, brew_seconds)
    key(name)
    values ('a213f3cc-a5ff-4ab8-b44e-d00a07db2cdc', 'CORTADO', 25.0, 16.0, 92.0, 'FINE', 20);

-- Seed machine snapshot (friendly but partly used state)
merge into coffee_machine (id, version, model,
    water_capacity, water_current,
    beans_capacity, beans_current,
    waste_capacity_pucks, waste_current_pucks,
    powered_on,
    is_brewing)
    key(id)
    values (
    uuid '00000000-0000-0000-0000-000000000000',
    1,
    'KarpCafé Deluxe',
    2000.0, 2000.0,
    500.0,  500.0,
    12,     0,
    false,
    false
    );
