CREATE TABLE IF NOT EXISTS clients (
                         id BIGSERIAL PRIMARY KEY,
                         name VARCHAR(100) NOT NULL,
                         email VARCHAR(100) NOT NULL UNIQUE,
                         phone VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS orders (
                        id BIGSERIAL PRIMARY KEY,
                        description VARCHAR(500) NOT NULL,
                        amount DECIMAL(10, 2) NOT NULL,
                        status VARCHAR(20) NOT NULL,
                        created_date TIMESTAMP NOT NULL,
                        client_id BIGINT NOT NULL,

                        CONSTRAINT fk_orders_clients
                            FOREIGN KEY (client_id)
                                REFERENCES clients (id)
                                ON DELETE RESTRICT,

                        CONSTRAINT chk_orders_status
                            CHECK (status IN ('NEW', 'IN_PROGRESS', 'DONE'))
);