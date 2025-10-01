package repository.impl;

import db.DBConnection;
import entity.Wallet;
import exceptions.NotFoundException;
import mapper.db.DBMapper;
import mapper.db.impl.WalletDbMapper;
import repository.interfaces.WalletRepository;
import util.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;


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

    @Override
    public Optional<Wallet> findByAddress(String address) {
        String sql = "SELECT * FROM wallets WHERE address = ?";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, address);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return Optional.of(getMapper().fromResult(rs));

        } catch (SQLException e) {
            Log.error(getClass(), "Error finding wallet by address in " + getTableName());
            throw new NotFoundException("Entity with address " + address + " not found in " + getTableName());
        }
        return Optional.empty();
    }
}