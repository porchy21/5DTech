CREATE TABLE IF NOT EXISTS company (
    id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
    name varchar(64) UNIQUE NOT NULL,
    contact_phone varchar(32) NOT NULL,
    budget integer NOT NULL
);

CREATE TABLE IF NOT EXISTS users (
    id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
    first_name varchar(32) NOT NULL,
    last_name varchar(32) NOT NULL,
    email varchar(64) UNIQUE NOT NULL,
    company_id bigint NOT NULL,

    CONSTRAINT fk_company FOREIGN KEY (company_id) REFERENCES company (id)
);



