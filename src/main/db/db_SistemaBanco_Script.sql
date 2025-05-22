-- Tabla de sucursales
CREATE TABLE branches (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    location VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla de usuarios
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password TEXT NOT NULL,
    dui VARCHAR(10) UNIQUE NOT NULL,
    role VARCHAR(30) NOT NULL CHECK (role IN ('cliente', 'dependiente', 'cajero', 'gerente_sucursal', 'gerente_general')),
    salary NUMERIC(10, 2),
    branch_id INTEGER REFERENCES branches(id) ON DELETE SET NULL,
    status VARCHAR(20) DEFAULT 'activo' CHECK (status IN ('activo', 'inactivo')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla de cuentas bancarias
CREATE TABLE bank_accounts (
    id BIGINT PRIMARY KEY, -- Generado aleatoriamente por el backend
    user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    balance NUMERIC(12, 2) DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla de transacciones
CREATE TABLE transactions (
    id SERIAL PRIMARY KEY,
    account_id BIGINT NOT NULL REFERENCES bank_accounts(id) ON DELETE CASCADE,
    type VARCHAR(20) NOT NULL CHECK (type IN ('deposito', 'retiro', 'transferencia')),
    amount NUMERIC(12, 2) NOT NULL CHECK (amount > 0),
    commission NUMERIC(12, 2),
    related_account_id BIGINT REFERENCES bank_accounts(id) ON DELETE SET NULL,
    performed_by INTEGER REFERENCES users(id) ON DELETE SET NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla de préstamos
CREATE TABLE loans (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    amount_requested NUMERIC(12, 2) NOT NULL,
    interest_rate NUMERIC(5, 2) NOT NULL,
    monthly_payment NUMERIC(12, 2) NOT NULL,
    years_to_pay INTEGER NOT NULL,
    status VARCHAR(20) NOT NULL CHECK (status IN ('en_espera', 'aprobado', 'rechazado')),
    created_by INTEGER NOT NULL REFERENCES users(id),
    approved_by INTEGER REFERENCES users(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla de acciones de personal
CREATE TABLE staff_actions (
    id SERIAL PRIMARY KEY,
    employee_id INTEGER NOT NULL REFERENCES users(id),
    action_type VARCHAR(10) NOT NULL CHECK (action_type IN ('alta', 'baja')),
    status VARCHAR(20) NOT NULL CHECK (status IN ('en_espera', 'aceptada', 'rechazada')),
    branch_id INTEGER NOT NULL REFERENCES branches(id),
    created_by INTEGER NOT NULL REFERENCES users(id),
    reviewed_by INTEGER REFERENCES users(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insersiones
-- GERENCIAS
INSERT INTO branches (name, location) VALUES
('Sucursal San Salvador Centro', 'Av. España #123, San Salvador'),
('Sucursal Santa Ana Norte', 'Calle Principal, Santa Ana'),
('Sucursal San Miguel Este', 'Av. Roosevelt, San Miguel');

-- USUARIOS
-- Gerente general
INSERT INTO users (name, email, password, dui, role, salary)
VALUES ('Guillermo Castillo', 'guillermo.castillo@banco.com', '123456', '01234567-8', 'gerente_general', 2500);

-- Gerente de sucursal
INSERT INTO users (name, email, password, dui, role, salary, branch_id)
VALUES ('Ana Ramírez', 'ana.ramirez@banco.com', '123456', '11223344-5', 'gerente_sucursal', 1800, 1);

-- Cajero
INSERT INTO users (name, email, password, dui, role, salary, branch_id)
VALUES ('Luis Gómez', 'luis.gomez@banco.com', '123456', '22334455-6', 'cajero', 900, 1);

-- Dependiente
INSERT INTO users (name, email, password, dui, role, salary)
VALUES ('Marta Torres', 'marta.torres@comercio.com', '123456', '33445566-7', 'dependiente', 0);

-- Clientes
INSERT INTO users (name, email, password, dui, role, salary)
VALUES 
('José López', 'jose.lopez@correo.com', '123456', '44556677-8', 'cliente', 600),
('Paola Martínez', 'paola.martinez@correo.com', '123456', '55667788-9', 'cliente', 350),
('David Hernández', 'david.hernandez@correo.com', '123456', '66778899-0', 'cliente', 1200);

-- CUENTAS BANCARIAS
INSERT INTO bank_accounts (id, user_id, balance) VALUES
(100000001, 5, 150.00),
(100000002, 5, 1200.00);

INSERT INTO bank_accounts (id, user_id, balance) VALUES
(100000003, 6, 50.00);

INSERT INTO bank_accounts (id, user_id, balance) VALUES
(100000004, 7, 5000.00),
(100000005, 7, 15000.00),
(100000006, 7, 2500.00);

-- TRANSACCIONES
INSERT INTO transactions (account_id, type, amount, performed_by)
VALUES (100000001, 'deposito', 500.00, 3);

INSERT INTO transactions (account_id, type, amount, commission, performed_by)
VALUES (100000002, 'retiro', 100.00, 5.00, 4);

INSERT INTO transactions (account_id, type, amount, related_account_id, performed_by)
VALUES (100000004, 'transferencia', 1000.00, 100000005, 7);

-- PRESTAMOS
-- Solicitud de préstamo por Paola (cliente con salario de $350)
INSERT INTO loans (user_id, amount_requested, interest_rate, monthly_payment, years_to_pay, status, created_by)
VALUES (6, 10000.00, 3.00, 90.00, 4, 'en_espera', 3);

-- Solicitud aprobada de David (cliente con salario de $1200)
INSERT INTO loans (user_id, amount_requested, interest_rate, monthly_payment, years_to_pay, status, created_by, approved_by)
VALUES (7, 50000.00, 5.00, 400.00, 5, 'aprobado', 3, 2);

-- ACCIONES DE PERSONAL
-- Gerente de sucursal solicita ingreso de nueva secretaria
INSERT INTO staff_actions (employee_id, action_type, status, branch_id, created_by)
VALUES (3, 'alta', 'en_espera', 1, 2);

-- Gerente general aprueba la acción
UPDATE staff_actions SET status = 'aceptada', reviewed_by = 1 WHERE id = 1;