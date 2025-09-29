package mapper.dto.interfaces;

import dto.request.TransactionRequestDTO;
import dto.response.TransactionResponseDTO;
import entity.Transaction;


public interface TransactionDTOMapper extends DTOMapper<Transaction, TransactionResponseDTO, TransactionRequestDTO> {
    
}
