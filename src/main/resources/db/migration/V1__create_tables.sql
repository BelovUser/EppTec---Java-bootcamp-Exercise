CREATE TABLE IF NOT EXISTS organization (
    id BIGINT PRIMARY KEY,
    organization_id BIGINT,
    name VARCHAR(255),
    address VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS employee (
    id BIGINT PRIMARY KEY,
    employee_id BIGINT,
    name VARCHAR(255),
    surname VARCHAR(255),
    birthday DATE,
    superior_id BIGINT,
    organization_id BIGINT,
    FOREIGN KEY (superior_id) REFERENCES employee(id),
    FOREIGN KEY (organization_id) REFERENCES organization(id)
);

