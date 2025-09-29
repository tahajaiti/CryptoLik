package repository.impl;

import db.DBConnection;
import entity.Transaction;
import mapper.db.DBMapper;
import mapper.db.impl.TransactionDbMapper;
import repository.interfaces.TransactionRepository;

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
        throw new UnsupportedOperationException("Update operation not supported for Transaction entity.");
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
}
