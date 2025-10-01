package service.interfaces;

import dto.request.TransactionRequestDTO;
import dto.response.TransactionResponseDTO;

import java.util.UUID;

public interface TransactionService {
    TransactionResponseDTO createTransaction(TransactionRequestDTO requestDTO);
    void applyTransaction(UUID txId);
    void generateRandomTransactions(int count);
}