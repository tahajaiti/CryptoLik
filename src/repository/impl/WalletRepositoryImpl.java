package repository.impl;

import db.DBConnection;
import entity.Wallet;
import mapper.db.DBMapper;
import mapper.db.impl.WalletDbMapper;
import repository.interfaces.WalletRepository;


public class WalletRepositoryImpl extends JdbcRepository<Wallet, Integer> implements WalletRepository{

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
        return "INSERT INTO wallets(type, address, balance, password) VALUES(?::wallet_type, ?, ?, ?) RETURNING id";
    }

    @Override
    protected Wallet setGeneratedId(Wallet entity, Object key) {
        if (key instanceof Integer) {
            entity.setId((Integer) key);
        } else {
            throw new IllegalStateException("Unexpected key type: " + key.getClass());
        }
        return entity;
    }

}