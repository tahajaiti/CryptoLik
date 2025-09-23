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
                ? new BitcoinWallet(rs.getDouble("balance"), rs.getString("address"))
                : new EthereumWallet(rs.getDouble("balance"), rs.getString("address"));
        wallet.setId(rs.getInt("id"));
        wallet.setPassword(rs.getString("password"));
        return wallet;
    }

    @Override
    protected void setInsertParams(PreparedStatement stmt, Wallet wallet) throws SQLException {
        stmt.setString(1, wallet.getWalletType().name());
        stmt.setString(2, wallet.getAddress());
        stmt.setDouble(3, wallet.getBalance());
        stmt.setString(4, wallet.getPassword());
    }

    @Override
    protected void setUpdateParams(PreparedStatement stmt, Wallet wallet) throws SQLException {
        stmt.setDouble(1, wallet.getBalance());
        stmt.setInt(2, wallet.getId());
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