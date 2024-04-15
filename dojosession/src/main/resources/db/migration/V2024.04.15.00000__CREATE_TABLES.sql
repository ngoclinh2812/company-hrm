CREATE TYPE status_enum AS ENUM ('INACTIVE', 'ACTIVE');
CREATE TYPE role_enum AS ENUM ('ROLE_USER', 'ROLE_ADMIN');
CREATE TYPE gender_enum AS ENUM ('MALE', 'FEMALE');

create table department
(
    id              bigserial primary key,
    department_name varchar(500)
);

create table employee
(
    id            bigserial primary key,
    email         varchar(500) unique not null,
    password      varchar(255)        not null,
    date_of_birth timestamp,
    phone         varchar(15) unique,
    first_name    varchar(255)        not null,
    middle_name   varchar(255),
    last_name     varchar(255),
    salary        int,
    department_id bigint references department (id),
    status        status_enum,
    role          role_enum,
    gender        gender_enum,
    created_at    timestamp,
    updated_at    timestamp
);

create table lunch_schedule
(
    id             bigserial primary key,
    start_date     timestamp not null,
    end_date       timestamp not null,
    order_deadline timestamp,
    created_at     timestamp,
    updated_at     timestamp
);

create table protein
(
    id           serial primary key,
    protein_type varchar(255) unique not null,
    is_vegan     boolean,
    created_at   timestamp,
    updated_at   timestamp
);

create table dish
(
    id         bigserial primary key,
    dish_name  varchar(255) not null,
    is_vegan   boolean,
    calories   int,
    protein_id int references protein (id),
    created_at timestamp,
    updated_at timestamp
);

create table menu
(
    id          bigserial primary key,
    menu_date timestamp,
    schedule_id bigint not null references lunch_schedule (id),
    created_at  timestamp,
    updated_at  timestamp
);

create table menu_dish
(
    id         bigserial primary key,
    menu_id    bigint not null references menu (id),
    dish_id    bigint not null references dish (id),
    created_at timestamp,
    updated_at timestamp
);

create table lunch_order
(
    id           bigserial primary key,
    menu_dish_id bigint references menu_dish (id),
    employee_id  bigint not null references employee (id),
    created_at   timestamp,
    updated_at   timestamp
);