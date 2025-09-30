package repository.impl;

import db.DBConnection;
import entity.Transaction;
import entity.enums.TransactionStatus;
import exceptions.NotFoundException;
import mapper.db.DBMapper;
import mapper.db.impl.TransactionDbMapper;
import repository.interfaces.TransactionRepository;
import util.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TransactionRepositoryImpl extends JdbcRepository<Transaction, UUID> implements TransactionRepository {

    private final DBMapper<Transaction> mapper;

    public TransactionRepositoryImpl(DBConnection dbConnection) {
        super(dbConnection);
        this.mapper = new TransactionDbMapper();
    }

    @Override
    protected String getTableName() {
        return "transactions";
    }

    @Override
    protected DBMapper<Transaction> getMapper() {
        return mapper;
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE " + getTableName() +
                " SET status = ?::transaction_status, mempool_position = ? WHERE id = ?::uuid";
    }


    @Override
    protected String getInsertQuery() {
        return "INSERT INTO transactions(" +
                "src_address, dest_address, amount, fee, fee_priority, status, wallet_type, timestamp, mempool_position" +
                ") VALUES(?, ?, ?, ?, ?::fee_priority, ?::transaction_status, ?::wallet_type, ?, ?)";
    }

    @Override
    protected Transaction setGeneratedId(Transaction entity, Object key) {
        if (key instanceof UUID) {
            entity.setId((UUID) key);
        } else {
            throw new IllegalStateException("Unexpected key type: " + key.getClass());
        }
        return entity;
    }

    @Override
    public List<Transaction> findByStatus(TransactionStatus status) {
        String sql = "SELECT * FROM " + getTableName() + " WHERE status = ?::transaction_status";
        List<Transaction> list = new ArrayList<>();

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status.name());

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(getMapper().fromResult(rs));
                }
            }

        } catch (SQLException e) {
            Log.error(getClass(), "Error finding all entities in " + getTableName(), e);
            throw new NotFoundException("No entities found in " + getTableName());
        }

        return list;
    }
}
