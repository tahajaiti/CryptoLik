package service.interfaces;

import dto.request.TransactionRequestDTO;
import dto.response.TransactionResponseDTO;

public interface TransactionService {
    TransactionResponseDTO createTransaction(TransactionRequestDTO requestDTO);
}