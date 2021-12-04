create table finance
(
    finance_id varchar(255) primary key NOT NULL,
    name       varchar(255)             NOT NULL
);

create table customer
(
    id                    BIGSERIAL PRIMARY KEY NOT NULL,
    finance_id_ref        varchar(255) REFERENCES finance (finance_id),
    name                  varchar(225)          not null,
    email                 varchar(255) default null,
    phone_number          varchar(255)          not null,
    total_amount          BIGINT                not null,
    remaining_amount      BIGINT                not null,
    interest_rate         numeric(3, 2),
    per_month_payment     BIGINT                not null,
    number_of_months      BIGINT                not null,
    lending_date          timestamptz           not null,
    expiry_date           timestamptz           not null,
    monthly_payment_day   numeric(2),
    first_witness_name    varchar(255) default null,
    first_witness_number  varchar(255) default null,
    second_witness_name   varchar(255) default null,
    second_witness_number varchar(255) default null,
    constraint uk_finance_id_and_name UNIQUE (finance_id_ref, name)
);

create table transaction
(
    id              BIGSERIAL primary key NOT NULL,
    finance_id_ref  varchar(255) REFERENCES finance (finance_id),
    customer_id_ref BIGINT REFERENCES customer (id),
    returned_amount BIGINT                not null,
    returned_date   timestamptz           not null,
    returned_to     varchar(255)
);

create table finance_users
(
    user_name   varchar(255),
    password    varchar(255),
    finance_ref varchar(255) REFERENCES finance (finance_id)
);