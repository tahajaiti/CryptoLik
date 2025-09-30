package repository.interfaces;

import entity.Transaction;
import entity.enums.TransactionStatus;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends Repository<Transaction, UUID>{
    List<Transaction> findByStatus(TransactionStatus status);
}
