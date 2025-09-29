package service.impl;

import dto.request.TransactionRequestDTO;
import dto.response.TransactionResponseDTO;
import entity.Transaction;
import entity.enums.FeePriority;
import entity.enums.WalletType;
import mapper.dto.interfaces.TransactionDTOMapper;
import repository.interfaces.TransactionRepository;
import service.interfaces.FeeCalculator;
import service.interfaces.TransactionService;
import util.TransactionValidator;

public class TransactionServiceImpl implements TransactionService {
    
    private final TransactionRepository txRepo;
    private final TransactionDTOMapper txMapper;

    public TransactionServiceImpl(TransactionRepository transactionRepository, TransactionDTOMapper transactionDTOMapper) {
        this.txRepo = transactionRepository;
        this.txMapper = transactionDTOMapper;
    }



    public TransactionResponseDTO createTransaction(TransactionRequestDTO requestDTO){
        WalletType walletType = requestDTO.getWalletType();

        FeeCalculator feeCalculator = walletType == WalletType.BITCOIN
            ? new BitcoinFeeCalculatorImpl()
            : new EthereumFeeCalculatorImpl();

        FeePriority feePriority = TransactionValidator.parseFeePriority(requestDTO.getFeePriority());

        double transactionFee = feeCalculator.calculateFee(feePriority);

        Transaction tx = new Transaction(
            requestDTO.getSrcAddress(),
            requestDTO.getDestAddress(),
            requestDTO.getAmount(),
            feePriority,
            transactionFee,
            walletType
        );

        Transaction savedTx = txRepo.save(tx);
        
        return txMapper.toResponseDTO(savedTx);
    }
}
