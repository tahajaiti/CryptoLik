package repository.impl;

import db.DBConnection;
import entity.Transaction;
<<<<<<< Updated upstream
import entity.Wallet;
import mapper.DB.DBMapper;
import mapper.DB.impl.TransactionDbMapper;
import mapper.DB.impl.WalletDbMapper;
=======
import mapper.db.DBMapper;
import mapper.db.impl.TransactionDbMapper;
>>>>>>> Stashed changes

public class TransactionRepositoryImpl extends JdbcRepository<Transaction>{

    private final DBMapper<Transaction> mapper = new TransactionDbMapper();

    public TransactionRepositoryImpl(DBConnection dbConnection) {
        super(dbConnection);
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
        return "INSERT INTO transactions(id, source_address, destination_address, amount, fee, fee_priority, status, created_at, position) " +
                "VALUES(?, ?, ?, ?, ?, ?::fee_priority, ?::transaction_status, ?, ?)";
    }
}
