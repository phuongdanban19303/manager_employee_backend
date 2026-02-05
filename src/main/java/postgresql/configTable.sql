-- =========================
-- tbl_user
-- =========================
CREATE TABLE public.tbl_user
(
    id         bigserial PRIMARY KEY,
    username   varchar(100) NOT NULL UNIQUE,
    fullname   varchar(100),
    password   varchar(255) NOT NULL,
    team       varchar(255),
    role       varchar(50)  NOT NULL,
    status     varchar(50)  NOT NULL,
    created_at timestamp DEFAULT CURRENT_TIMESTAMP
);

-- =========================
-- tbl_employee
-- =========================
CREATE TABLE public.tbl_employee
(
    id                 bigserial PRIMARY KEY,
    employee_code      varchar(50) NOT NULL UNIQUE,
    full_name          varchar(255),
    gender             varchar(10),
    date_of_birth      date,
    address            varchar(500),
    team               varchar(255),
    avatar_url         varchar(500),
    identity_number    varchar(50),
    phone              varchar(20),
    email              varchar(255),
    status             varchar(50) NOT NULL,
    status_updated_at  timestamp,
    terminated_at      date,
    termination_reason varchar(500),
    archive_date       date,
    archive_number     varchar(100),
    created_by         bigint      NOT NULL,
    created_at         timestamp DEFAULT CURRENT_TIMESTAMP,
    updated_by         bigint,
    updated_at         timestamp DEFAULT CURRENT_TIMESTAMP
);

-- =========================
-- employee_certificates
-- =========================
CREATE TABLE public.employee_certificates
(
    id          bigserial PRIMARY KEY,
    employee_id bigint       NOT NULL,
    name        varchar(200) NOT NULL,
    issue_date  date,
    content     text,
    field_url   varchar(100),
    created_by  bigint       NOT NULL,
    created_at  timestamp DEFAULT CURRENT_TIMESTAMP,
    updated_by  bigint,
    updated_at  timestamp DEFAULT CURRENT_TIMESTAMP
);

-- =========================
-- employee_family_relations
-- =========================
CREATE TABLE public.employee_family_relations
(
    id                   bigserial PRIMARY KEY,
    employee_id          bigint       NOT NULL,
    full_name            varchar(100) NOT NULL,
    gender               int,
    date_of_birth        date,
    identity_card_number varchar(20),
    relationship         varchar(50),
    address              text,
    created_by           bigint       NOT NULL,
    created_at           timestamp DEFAULT CURRENT_TIMESTAMP,
    updated_by           bigint,
    updated_at           timestamp DEFAULT CURRENT_TIMESTAMP
);

-- =========================
-- form_employee_registration
-- =========================
CREATE TABLE public.form_employee_registration
(
    id           bigserial PRIMARY KEY,
    employee_id  bigint      NOT NULL,
    resume       text,
    cv_url       varchar(255),
    note         text,
    job_position varchar(50),
    receiver_id  bigint,
    status       varchar(50) NOT NULL,
    submit_date  timestamp,
    approve_date timestamp,
    leader_note  text,
    created_by   bigint      NOT NULL,
    created_at   timestamp DEFAULT CURRENT_TIMESTAMP,
    updated_by   bigint,
    updated_at   timestamp DEFAULT CURRENT_TIMESTAMP
);

-- =========================
-- form_salary_increase
-- =========================
CREATE TABLE public.form_salary_increase
(
    id           bigserial PRIMARY KEY,
    employee_id  bigint      NOT NULL,
    times        int,
    old_level    varchar(50),
    new_level    varchar(50),
    reason       text,
    receiver_id  bigint,
    status       varchar(50) NOT NULL,
    submit_date  timestamp,
    approve_date timestamp,
    leader_note  text,
    created_by   bigint      NOT NULL,
    created_at   timestamp DEFAULT CURRENT_TIMESTAMP,
    updated_by   bigint,
    updated_at   timestamp DEFAULT CURRENT_TIMESTAMP
);

-- =========================
-- form_promotion
-- =========================
CREATE TABLE public.form_promotion
(
    id           bigserial PRIMARY KEY,
    employee_id  bigint      NOT NULL,
    old_position varchar(100),
    new_position varchar(100),
    reason       text,
    receiver_id  bigint,
    status       varchar(50) NOT NULL,
    submit_date  timestamp,
    approve_date timestamp,
    leader_note  text,
    created_by   bigint      NOT NULL,
    created_at   timestamp DEFAULT CURRENT_TIMESTAMP,
    updated_by   bigint,
    updated_at   timestamp DEFAULT CURRENT_TIMESTAMP
);

-- =========================
-- form_proposal
-- =========================
CREATE TABLE public.form_proposal
(
    id           bigserial PRIMARY KEY,
    employee_id  bigint      NOT NULL,
    content      text,
    detail       text,
    receiver_id  bigint,
    status       varchar(50) NOT NULL,
    submit_date  timestamp,
    approve_date timestamp,
    leader_note  text,
    created_by   bigint      NOT NULL,
    created_at   timestamp DEFAULT CURRENT_TIMESTAMP,
    updated_by   bigint,
    updated_at   timestamp DEFAULT CURRENT_TIMESTAMP
);

-- =========================
-- form_contract_termination
-- =========================
CREATE TABLE public.form_contract_termination
(
    id                 bigserial PRIMARY KEY,
    employee_id        bigint      NOT NULL,
    termination_date   date,
    termination_reason text,
    receiver_id        bigint,
    status             varchar(50) NOT NULL,
    submit_date        timestamp,
    approve_date       timestamp,
    leader_note        text,
    created_by         bigint      NOT NULL,
    created_at         timestamp DEFAULT CURRENT_TIMESTAMP,
    updated_by         bigint,
    updated_at         timestamp DEFAULT CURRENT_TIMESTAMP
);
