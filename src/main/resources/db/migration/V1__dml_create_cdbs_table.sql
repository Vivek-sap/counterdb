CREATE TABLE counter (
    id                       CHAR(36)        NOT NULL PRIMARY KEY,
    creation_time            TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_update_time         TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    version                  INTEGER         NOT NULL DEFAULT 0,
    counter_value            INTEGER         NOT NULL
);

INSERT INTO counter
(id, counter_value) VALUES (uuid(),0);




