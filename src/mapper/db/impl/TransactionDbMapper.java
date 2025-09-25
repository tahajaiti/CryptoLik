package mapper.db.impl;

import entity.Transaction;
import entity.enums.FeePriority;
import entity.enums.TransactionStatus;
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
                rs.getString("source_address"),
                rs.getString("destination_address"),
                rs.getDouble("amount"),
                FeePriority.valueOf(rs.getString("fee_priority")),
                rs.getDouble("fees")
        );

        String idStr = rs.getString("id");
        if (idStr != null) {
            tx.setId(UUID.fromString(idStr));
        }

        tx.setStatus(TransactionStatus.valueOf(rs.getString("status")));

        tx.setMempoolPosition(rs.getInt("position"));

        Timestamp ts = rs.getTimestamp("created_at");
        if (ts != null) {
            tx.setTimestamp(ts.toLocalDateTime());
        }

        return tx;
    }

    @Override
    public void toInsertStmt(PreparedStatement stmt, Transaction tx) throws SQLException {
        stmt.setString(1, tx.getId().toString());
        stmt.setString(2, tx.getSrcAddress());
        stmt.setString(3, tx.getDestAddress());
        stmt.setDouble(4, tx.getAmount());
        stmt.setDouble(5, tx.getFee());
        stmt.setString(6, tx.getFeePriority().name());
        stmt.setString(7, tx.getStatus().name());
        stmt.setTimestamp(8, Timestamp.valueOf(tx.getTimestamp()));
        stmt.setInt(9, tx.getMempoolPosition());
    }

    @Override
    public void toUpdateStmt(PreparedStatement stmt, Transaction tx) throws SQLException {
        throw new SQLException("Update operation not supported for Transaction entity.");
    }

    @Override
    public Transaction setId(Transaction entity, int id) {
        // Transaction ID is a UUID and is set during creation, so this method is not applicable.
        return entity;
    }
}
