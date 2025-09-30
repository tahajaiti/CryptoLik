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
import service.interfaces.MempoolService;
import util.Log;
import util.TransactionValidator;

public class TransactionServiceImpl implements TransactionService {
    
    private final TransactionRepository txRepo;
    private final TransactionDTOMapper txMapper;
    private final MempoolService mempoolService;

    public TransactionServiceImpl(TransactionRepository transactionRepository, TransactionDTOMapper transactionDTOMapper, MempoolService mempoolService) {
        this.mempoolService = mempoolService;
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

        double finalAmount = requestDTO.getAmount() + transactionFee;

        Log.info(getClass(), "Calculated transaction fee: " + transactionFee);

        Transaction tx = new Transaction(
            requestDTO.getSrcAddress(),
            requestDTO.getDestAddress(),
            finalAmount,
            feePriority,
            transactionFee,
            walletType
        );

        Log.info(getClass(), "Creating transaction: " + tx.toString());

        Transaction savedTx = txRepo.save(tx);

        Log.info(getClass(), "Transaction created: " + savedTx.toString());

        mempoolService.addTransaction(savedTx);
        
        return txMapper.toResponseDTO(savedTx);
    }
}
