package mapper.DB.impl;

import entity.BitcoinWallet;
import entity.EthereumWallet;
import entity.Wallet;
import entity.enums.WalletType;
import mapper.DB.DBMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WalletDbMapper implements DBMapper<Wallet> {

    @Override
    public Wallet fromResult(ResultSet rs) throws SQLException {
        WalletType type = WalletType.valueOf(rs.getString("wallet_type"));
        Wallet wallet = (type == WalletType.BITCOIN)
                ? new BitcoinWallet(rs.getDouble("balance"), rs.getString("address"))
                : new EthereumWallet(rs.getDouble("balance"), rs.getString("address"));

        wallet.setId(rs.getInt("id"));
        wallet.setPassword(rs.getString("password"));
        return wallet;
    }

    @Override
    public void toInsertStmt(PreparedStatement stmt, Wallet wallet) throws SQLException {
        stmt.setString(1, wallet.getWalletType().name());
        stmt.setDouble(2, wallet.getBalance());
        stmt.setString(3, wallet.getAddress());
        stmt.setString(4, wallet.getPassword());
    }

    @Override
    public void toUpdateStmt(PreparedStatement stmt, Wallet wallet) throws SQLException {
        stmt.setDouble(1, wallet.getBalance());
        stmt.setInt(2, wallet.getId());
    }
}
