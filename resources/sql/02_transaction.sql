DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'fee_priority') THEN
CREATE TYPE fee_priority AS ENUM ('ECONOMIC', 'STANDARD', 'RAPID');
END IF;

    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'transaction_status') THEN
CREATE TYPE transaction_status AS ENUM ('PENDING', 'COMPLETED', 'FAILED');
END IF;
END
$$;

CREATE TABLE IF NOT EXISTS transactions (
                                            id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    src_address VARCHAR(100) NOT NULL,
    dest_address VARCHAR(100) NOT NULL,
    amount NUMERIC(38, 18) NOT NULL,
    fee NUMERIC(38, 18) NOT NULL,
    fee_priority fee_priority NOT NULL,
    status transaction_status NOT NULL DEFAULT 'PENDING',
    wallet_type wallet_type NOT NULL,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    mempool_position INT DEFAULT -1
    );

CREATE INDEX IF NOT EXISTS idx_transactions_src_address ON transactions(src_address);
CREATE INDEX IF NOT EXISTS idx_transactions_dest_address ON transactions(dest_address);
