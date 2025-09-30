package mapper.db.impl;

import entity.Transaction;
import entity.enums.FeePriority;
import entity.enums.TransactionStatus;
import entity.enums.WalletType;
import mapper.db.DBMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.UUID;

public class TransactionDbMapper implements DBMapper<Transaction> {

    @Override
    public Transaction fromResult(ResultSet rs) throws SQLException {
        Transaction tx = new Transaction(
                rs.getString("src_address"),
                rs.getString("dest_address"),
                rs.getDouble("amount"),
                FeePriority.valueOf(rs.getString("fee_priority")),
                rs.getDouble("fee"),
                WalletType.valueOf(rs.getString("wallet_type"))
        );

        String idStr = rs.getString("id");
        if (idStr != null) {
            tx.setId(UUID.fromString(idStr));
        }

        tx.setStatus(TransactionStatus.valueOf(rs.getString("status")));

        tx.setMempoolPosition(rs.getInt("mempool_position"));

        Timestamp ts = rs.getTimestamp("timestamp");
        if (ts != null) {
            tx.setTimestamp(ts.toLocalDateTime());
        }

        return tx;
    }

    @Override
    public void toInsertStmt(PreparedStatement stmt, Transaction tx) throws SQLException {
        stmt.setString(1, tx.getSrcAddress());
        stmt.setString(2, tx.getDestAddress());
        stmt.setDouble(3, tx.getAmount());
        stmt.setDouble(4, tx.getFee());
        stmt.setString(5, tx.getFeePriority().name());
        stmt.setString(6, tx.getStatus().name());
        stmt.setString(7, tx.getWalletType().name());
        stmt.setTimestamp(8, Timestamp.valueOf(tx.getTimestamp()));
        stmt.setInt(9, tx.getMempoolPosition());
    }

    @Override
    public void toUpdateStmt(PreparedStatement stmt, Transaction tx) throws SQLException {
        stmt.setString(1, tx.getStatus().name());
        stmt.setInt(2, tx.getMempoolPosition());
        stmt.setString(3, tx.getId().toString());
    }

}
