package mapper.DTO.impl;

import dto.request.TransactionRequestDTO;
import dto.response.TransactionResponseDTO;
import entity.Transaction;
import mapper.DTO.DTOMapper;

public class TransactionDtoMapper implements DTOMapper<Transaction, TransactionResponseDTO, TransactionRequestDTO> {

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
