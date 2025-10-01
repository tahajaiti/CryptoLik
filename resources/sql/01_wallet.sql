DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'wallet_type') THEN
        CREATE TYPE wallet_type AS ENUM ('BITCOIN', 'ETHEREUM');
    END IF;
END
$$;

CREATE TABLE IF NOT EXISTS wallets (
    id SERIAL PRIMARY KEY,
    type wallet_type NOT NULL,
    address VARCHAR(100) UNIQUE NOT NULL,
    balance NUMERIC(38, 18) DEFAULT 0 NOT NULL,
    password TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_wallet_type ON wallets(type);
CREATE INDEX IF NOT EXISTS idx_wallet_address ON wallets(address);
CREATE INDEX IF NOT EXISTS idx_wallet_id ON wallets(id);

DO $$
    BEGIN
        IF NOT EXISTS (SELECT 1 FROM wallets WHERE address = 'bc1MxK2QsDf4wp3bdXe7DPHe5uEJuHLdEjTS4') THEN
            INSERT INTO wallets (type, address, balance, password)
            VALUES
                ('BITCOIN', 'bc1MxK2QsDf4wp3bdXe7DPHe5uEJuHLdEjTS4', 10000, 'password'),
                ('ETHEREUM', '0xjdsLDAW3sOd3dc9km2Fl21l04eJzNX02pel08', 10000, 'password');
        END IF;
    END
$$;