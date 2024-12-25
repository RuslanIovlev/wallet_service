CREATE TABLE wallets(
    wallet_id UUID PRIMARY KEY,
    balance numeric(10,2),
    version bigint DEFAULT 0
);
