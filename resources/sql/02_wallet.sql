DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'wallet_type') THEN
CREATE TYPE wallet_type AS ENUM ('BITCOIN', 'ETHEREUM');
END IF;
END
$$;

CREATE TABLE IF NOT EXISTS wallets (
                                       id UUID PRIMARY KEY,
                                       type wallet_type NOT NULL,
                                       address VARCHAR(100) UNIQUE NOT NULL,
    balance NUMERIC(38, 18) DEFAULT 0 NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );
