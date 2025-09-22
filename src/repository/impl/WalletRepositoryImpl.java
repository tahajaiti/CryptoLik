package repository.impl;

import db.DBConnection;
import entity.BitcoinWallet;
import entity.EthereumWallet;
import entity.Wallet;
import entity.enums.WalletType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WalletRepositoryImpl extends JdbcRepository<Wallet> {

    public WalletRepositoryImpl(DBConnection dbConnection) {
        super(dbConnection);
    }

    @Override
    protected String getTableName() {
        return "wallets";
    }

    @Override
    protected Wallet mapToEntity(ResultSet rs) throws SQLException {
        WalletType type = WalletType.valueOf(rs.getString("type"));
        Wallet wallet = type == WalletType.BITCOIN
                ? new BitcoinWallet(rs.getInt("id"), rs.getDouble("balance"))
                : new EthereumWallet(rs.getInt("id"), rs.getDouble("balance"));
        wallet.setAddress(rs.getString("address"));
        wallet.setId(rs.getInt("id"));
        return wallet;
    }

    @Override
    protected void setInsertParams(PreparedStatement stmt, Wallet wallet) throws SQLException {
        stmt.setInt(1, wallet.getId());
        stmt.setString(2, wallet.getWalletType().name());
        stmt.setString(3, wallet.getAddress());
        stmt.setDouble(4, wallet.getBalance());
    }

    @Override
    protected void setUpdateParams(PreparedStatement stmt, Wallet wallet) throws SQLException {
        stmt.setDouble(2, wallet.getBalance());
        stmt.setInt(3, wallet.getId());
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE wallets SET balance = ? WHERE id = ?";
    }

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO wallets(id, type, address, balance) VALUES(?, ?, ?, ?)";
    }
}