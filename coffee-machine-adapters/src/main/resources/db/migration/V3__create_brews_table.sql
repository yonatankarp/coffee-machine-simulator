CREATE TABLE brews (
                       id             UUID PRIMARY KEY,
                       version        BIGINT           NOT NULL,
                       machine_id     UUID             NOT NULL,
                       recipe_id      UUID             NOT NULL,
                       state          VARCHAR(255)     NOT NULL,
                       total_seconds  DOUBLE PRECISION NOT NULL,
                       consumed_water DOUBLE PRECISION NOT NULL,
                       consumed_beans DOUBLE PRECISION NOT NULL,
                       started_at     TIMESTAMP        NOT NULL,
                       finished_at    TIMESTAMP,
                       cancelled_at   TIMESTAMP,

                       CONSTRAINT fk_recipe FOREIGN KEY (recipe_id) REFERENCES recipes(id)
);
