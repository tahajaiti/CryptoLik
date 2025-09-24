package repository.impl;

import db.DBConnection;
import entity.Wallet;
import mapper.DB.DBMapper;
import mapper.DB.impl.WalletDbMapper;


public class WalletRepositoryImpl extends JdbcRepository<Wallet> {

    private final DBMapper<Wallet> mapper = new WalletDbMapper();

    public WalletRepositoryImpl(DBConnection dbConnection) {
        super(dbConnection);
    }

    @Override
    protected String getTableName() {
        return "wallets";
    }

    @Override
    protected DBMapper<Wallet> getMapper() {
        return mapper;
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE wallets SET balance = ? WHERE id = ?";
    }

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO wallets(type, address, balance, password) VALUES(?::wallet_type, ?, ?, ?)";
    }

}