package mapper.dto.impl;

import dto.request.TransactionRequestDTO;
import dto.response.TransactionResponseDTO;
import entity.Transaction;
import mapper.dto.interfaces.TransactionDTOMapper;

public class TransactionDtoMapper implements TransactionDTOMapper {

    @Override
    public TransactionResponseDTO toResponseDTO(Transaction tx) {
        return new TransactionResponseDTO(
                tx.getId(),
                tx.getSrcAddress(),
                tx.getDestAddress(),
                tx.getAmount(),
                tx.getFee(),
                tx.getStatus(),
                tx.getTimestamp()
        );
    }

    @Override
    public void updateFromRequest(Transaction tx, TransactionRequestDTO requestDTO) {
        throw new RuntimeException("Update operation not supported for Transaction entity.");
    }

}
