-- V2: seed initial recipes and one coffee machine snapshot

merge into recipe (id, name, water, beans, temperature, grind, brew_seconds)
    key(name)
    values (random_uuid(), 'ESPRESSO', 30.0, 18.0, 93.0, 'FINE', 25);

merge into recipe (id, name, water, beans, temperature, grind, brew_seconds)
    key(name)
    values (random_uuid(), 'RISTRETTO', 22.0, 16.0, 94.0, 'FINE', 20);

merge into recipe (id, name, water, beans, temperature, grind, brew_seconds)
    key(name)
    values (random_uuid(), 'LUNGO', 60.0, 18.0, 92.0, 'MEDIUM', 35);

merge into recipe (id, name, water, beans, temperature, grind, brew_seconds)
    key(name)
    values (random_uuid(), 'AMERICANO', 120.0, 18.0, 93.0, 'MEDIUM', 25);

merge into recipe (id, name, water, beans, temperature, grind, brew_seconds)
    key(name)
    values (random_uuid(), 'LONG_BLACK', 100.0, 18.0, 93.0, 'MEDIUM', 25);

merge into recipe (id, name, water, beans, temperature, grind, brew_seconds)
    key(name)
    values (random_uuid(), 'LATTE', 35.0, 18.0, 93.0, 'MEDIUM', 30);

merge into recipe (id, name, water, beans, temperature, grind, brew_seconds)
    key(name)
    values (random_uuid(), 'CAPPUCCINO', 30.0, 18.0, 93.0, 'FINE', 25);

merge into recipe (id, name, water, beans, temperature, grind, brew_seconds)
    key(name)
    values (random_uuid(), 'FLAT_WHITE', 30.0, 18.0, 92.0, 'FINE', 25);

merge into recipe (id, name, water, beans, temperature, grind, brew_seconds)
    key(name)
    values (random_uuid(), 'DOUBLE_ESPRESSO', 60.0, 36.0, 94.0, 'FINE', 35);

merge into recipe (id, name, water, beans, temperature, grind, brew_seconds)
    key(name)
    values (random_uuid(), 'CORTADO', 25.0, 16.0, 92.0, 'FINE', 20);

-- Seed machine snapshot (friendly but partly used state)
merge into coffee_machine (id, version, model,
    water_capacity, water_current,
    beans_capacity, beans_current,
    waste_capacity_pucks, waste_current_pucks,
    powered_on)
    key(id)
    values (
    uuid '00000000-0000-0000-0000-000000000000',
    1,
    'KarpCafé Deluxe',
    2000.0, 2000.0,
    500.0,  500.0,
    12,     0,
    false
    );
