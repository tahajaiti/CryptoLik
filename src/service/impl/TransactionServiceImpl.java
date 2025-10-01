package service.impl;

import dto.request.TransactionRequestDTO;
import dto.response.TransactionResponseDTO;
import entity.Transaction;
import entity.Wallet;
import entity.enums.FeePriority;
import entity.enums.TransactionStatus;
import entity.enums.WalletType;
import exceptions.MempoolException;
import exceptions.NotFoundException;
import mapper.dto.interfaces.TransactionDTOMapper;
import repository.interfaces.TransactionRepository;
import repository.interfaces.WalletRepository;
import service.interfaces.*;
import util.Log;
import util.TransactionValidator;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository txRepo;
    private final TransactionDTOMapper txMapper;
    private final MempoolService mempoolService;
    private final WalletRepository walletRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository,
                                  TransactionDTOMapper transactionDTOMapper,
                                  MempoolService mempoolService,
                                  WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
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

    @Override
    public void applyTransaction(UUID txId) {
        Transaction tx = txRepo.findById(txId)
                .orElseThrow(() -> new NotFoundException("Transaction with ID " + txId + " does not exist"));

        if (tx.getAmount() <= 0) {
            Log.error(getClass(), "Invalid transaction amount: " + tx);
            throw new IllegalArgumentException("Transaction amount must be positive.");
        }

        // getting the sender
        Wallet sender = walletRepository.findByAddress(tx.getSrcAddress())
                .orElseThrow(() -> new NotFoundException("Wallet with address " + tx.getSrcAddress() + " not found"));

        // getting the receiver
        Wallet receiver = walletRepository.findByAddress(tx.getDestAddress())
                .orElseThrow(() -> new NotFoundException("Wallet with address " + tx.getDestAddress() + " not found"));

        double totalDebit = tx.getAmount();
        double transferAmount = tx.getAmount() - tx.getFee();

        if (sender.getBalance() < totalDebit) {
            Log.error(getClass(), "Insufficient balance for transaction: " + tx);
            throw new IllegalArgumentException("Insufficient balance for transaction.");
        }

        try {
            sender.setBalance(sender.getBalance() - totalDebit);
            walletRepository.update(sender);

            receiver.setBalance(receiver.getBalance() + transferAmount);
            walletRepository.update(receiver);

            Log.info(getClass(), "Transaction applied: " + tx);
            Log.info(getClass(), "Sender new balance: " + sender.getBalance());
            Log.info(getClass(), "Receiver new balance: " + receiver.getBalance());
            Log.info(getClass(), "Fee collected: " + tx.getFee());

        } catch (Exception e) {
            Log.error(getClass(), "Error applying transaction " + tx + " - " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void generateRandomTransactions(int count) {
        try {
            List<Wallet> wallets = walletRepository.findAll();
            if (wallets.isEmpty()) {
                throw new IllegalStateException("Need at least 1 wallet to generate transactions.");
            }

            if (wallets.size() == 1) {
                Log.warn(getClass(), "Only one wallet available. Cannot generate transactions between different addresses.");
                return;
            }

            for (int i = 0; i < count; i++) {
                int senderIndex = ThreadLocalRandom.current().nextInt(wallets.size());
                int receiverIndex;
                do {
                    receiverIndex = ThreadLocalRandom.current().nextInt(wallets.size());
                } while (receiverIndex == senderIndex);

                Wallet sender = wallets.get(senderIndex);
                Wallet receiver = wallets.get(receiverIndex);

                FeePriority priority = FeePriority.values()[ThreadLocalRandom.current().nextInt(FeePriority.values().length)];

                FeeCalculator feeCalculator = sender.getWalletType() == WalletType.BITCOIN
                        ? new BitcoinFeeCalculatorImpl()
                        : new EthereumFeeCalculatorImpl();

                double fee = feeCalculator.calculateFee(priority);
                double amount = ThreadLocalRandom.current().nextDouble(1.0, 50.0);
                double finalAmount = amount + fee;

                Transaction tx = new Transaction(
                        sender.getAddress(),
                        receiver.getAddress(),
                        finalAmount,
                        priority,
                        fee,
                        sender.getWalletType()
                );
                tx.setStatus(TransactionStatus.PENDING);

                Transaction saved = txRepo.save(tx);
                mempoolService.addTransaction(saved);

                Log.info(getClass(), "Generated random TX " + saved.getId().toString().substring(0, 8)
                        + " from " + sender.getAddress().substring(0, 6) + "..."
                        + " → " + receiver.getAddress().substring(0, 6) + "..."
                        + " fee=" + fee + " priority=" + priority);
            }
        } catch (Exception e) {
            Log.error(getClass(), "Error generating random transactions", e);
            throw new MempoolException("Failed to generate random transactions", e);
        }
    }
}
