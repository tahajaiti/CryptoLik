package service.impl;

import config.MempoolConfig;
import dto.response.FeeComparisonDTO;
import dto.response.MempoolPositionResponseDTO;
import dto.response.MempoolStateResponseDTO;
import dto.response.WalletResponseDTO;
import entity.Transaction;
import entity.enums.FeePriority;
import entity.enums.TransactionStatus;
import exceptions.MempoolException;
import exceptions.MempoolFullException;
import exceptions.MempoolProcessingException;
import exceptions.TransactionNotFoundException;
import repository.interfaces.TransactionRepository;
import service.interfaces.MempoolService;
import util.Log;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.stream.Collectors;

public class MempoolServiceImpl implements MempoolService {

    private volatile boolean running = true;
    private final TransactionRepository txRepository;
    private final Map<String, Transaction> mempoolCache;
    private final PriorityBlockingQueue<Transaction> mempoolQueue;

    private final Comparator<Transaction> txComparator = (a, b) -> {
        int priorityComparison = a.getFeePriority().ordinal() - b.getFeePriority().ordinal();
        if (priorityComparison != 0) return priorityComparison;
        return Double.compare(b.getFee(), a.getFee());
    };

    public MempoolServiceImpl(TransactionRepository txRepository) {
        this.txRepository = txRepository;
        this.mempoolCache = new ConcurrentHashMap<>();
        this.mempoolQueue = new PriorityBlockingQueue<>(MempoolConfig.MAX_MEMPOOL_SIZE, txComparator);
        boot();
    }

    // ====================
    //  Boot & Initialization
    // ====================
    private void boot() {
        try {
            List<Transaction> pendingTx = txRepository.findByStatus(TransactionStatus.PENDING);
            for (Transaction tx : pendingTx) {
                mempoolCache.putIfAbsent(tx.getId().toString(), tx);
                mempoolQueue.offer(tx);
                Log.info(getClass(), "Loaded pending transaction into mempool: " + tx.getId().toString().substring(0, 8) + "...");
            }
            Log.info(getClass(), "MempoolService started with " + pendingTx.size() + " pending transactions.");
        } catch (Exception e) {
            Log.error(getClass(), "MempoolService failed to boot", e);
        }
    }

    // ====================
    //  Mempool Processing
    // ====================
    private void processTransactions() throws InterruptedException {
        for (int i = 0; i < MempoolConfig.PROCESS_BATCH_SIZE; i++) {
            Transaction tx = mempoolQueue.poll();
            if (tx == null) break;

            try {
                int processingTimeMs;
                switch (tx.getFeePriority()) {
                    case ECONOMIC: processingTimeMs = 5000; break;
                    case STANDARD: processingTimeMs = 2500; break;
                    case RAPID: processingTimeMs = 1000; break;
                    default: processingTimeMs = 2000; break;
                }

                Thread.sleep(processingTimeMs);
                tx.setStatus(TransactionStatus.COMPLETED);
                txRepository.update(tx);
                mempoolCache.remove(tx.getId().toString());

                updateMempoolPositions();
                Log.info(getClass(), "Processed transaction: " + tx.getId().toString().substring(0, 8) + "...");
            } catch (Exception e) {
                Log.error(getClass(), "Failed to process transaction: " + tx.getId(), e);
                throw new MempoolProcessingException("Failed to process transaction: " + tx.getId(), e);
            }
        }
    }

    // ====================
    //  Position Updates
    // ====================
    private void updateMempoolPositions() {
        List<Transaction> ordered = mempoolQueue.stream()
                .sorted(txComparator)
                .collect(Collectors.toList());

        for (int i = 0; i < ordered.size(); i++) {
            Transaction tx = ordered.get(i);
            tx.setMempoolPosition(i + 1);
            txRepository.update(tx);
        }
    }

    // ====================
    //  Transaction Management
    // ====================
    @Override
    public void addTransaction(Transaction tx) {
        try {
            if (mempoolCache.putIfAbsent(tx.getId().toString(), tx) == null) {
                mempoolQueue.offer(tx);
                updateMempoolPositions();
                Log.info(getClass(), "Transaction added to mempool: " + tx.getId().toString().substring(0, 8) + "...");

                if (mempoolQueue.size() > MempoolConfig.MAX_MEMPOOL_SIZE) {
                    Transaction removedTx = mempoolQueue.poll();
                    if (removedTx != null) {
                        mempoolCache.remove(removedTx.getId().toString());
                        Log.warn(getClass(), "Mempool full. Removed lowest priority transaction: " + removedTx.getId().toString().substring(0, 8) + "...");
                        throw new MempoolFullException();
                    }
                }
            } else {
                Log.warn(getClass(), "Transaction already in mempool: " + tx.getId().toString().substring(0, 8) + "...");
                throw new MempoolException("Transaction already exists in mempool: " + tx.getId());
            }
        } catch (Exception e) {
            Log.error(getClass(), "Error adding transaction to mempool", e);
            throw new MempoolProcessingException("Failed to add transaction: " + tx.getId(), e);
        }
    }

    // ====================
    //  Mempool Queries
    // ====================
    @Override
    public MempoolPositionResponseDTO getPosition(String txId) {
        List<Transaction> ordered = mempoolQueue.stream().sorted(txComparator).collect(Collectors.toList());

        for (int i = 0; i < ordered.size(); i++) {
            Transaction tx = ordered.get(i);
            if (tx.getId().toString().equals(txId)) {
                int etaMs = 0;
                for (int j = 0; j < i; j++) {
                    switch (ordered.get(j).getFeePriority()) {
                        case ECONOMIC: etaMs += 5000; break;
                        case STANDARD: etaMs += 2500; break;
                        case RAPID: etaMs += 1000; break;
                    }
                }
                return new MempoolPositionResponseDTO(i + 1, ordered.size(), etaMs / 1000);
            }
        }

        Log.warn(getClass(), "Transaction not found in mempool: " + txId);
        throw new TransactionNotFoundException(txId);
    }

    @Override
    public List<FeeComparisonDTO> compareFeeLevels() {
        List<Transaction> sorted = mempoolQueue.stream().sorted(txComparator).collect(Collectors.toList());
        for (int i = 0; i < sorted.size(); i++) sorted.get(i).setMempoolPosition(i + 1);

        List<FeeComparisonDTO> result = new ArrayList<>();
        for (FeePriority p : FeePriority.values()) {
            List<Transaction> txs = new ArrayList<>();
            for (Transaction t : sorted) if (t.getFeePriority() == p) txs.add(t);
            if (txs.isEmpty()) continue;

            int minPos = Integer.MAX_VALUE;
            int maxPos = Integer.MIN_VALUE;
            double sumFee = 0;
            for (Transaction t : txs) {
                minPos = Math.min(minPos, t.getMempoolPosition());
                maxPos = Math.max(maxPos, t.getMempoolPosition());
                sumFee += t.getFee();
            }
            double avgFee = sumFee / txs.size();

            String speed;
            int estTime;
            switch (p) {
                case RAPID: speed = "Fast"; estTime = 10; break;
                case STANDARD: speed = "Medium"; estTime = 20; break;
                case ECONOMIC: speed = "Slow"; estTime = 30; break;
                default: speed = "?"; estTime = -1; break;
            }

            result.add(new FeeComparisonDTO(p, minPos, maxPos, avgFee, speed, estTime));
        }
        return result;
    }

    @Override
    public MempoolStateResponseDTO getCurrentMempoolState(WalletResponseDTO userWallet) {
        List<Transaction> ordered = mempoolQueue.stream().sorted(txComparator).collect(Collectors.toList());
        List<MempoolStateResponseDTO.TransactionEntry> entries = new ArrayList<>();

        for (Transaction tx : ordered) {
            boolean isUserTx = userWallet != null &&
                    userWallet.getAddress().equalsIgnoreCase(tx.getSrcAddress());
            String hashShort = tx.getId().toString().substring(0, 6) + "...";

            entries.add(new MempoolStateResponseDTO.TransactionEntry(hashShort, tx.getFee(), isUserTx));
        }

        return new MempoolStateResponseDTO(ordered.size(), entries);
    }

    // ====================
    //  Runnable Implementation
    // ====================
    @Override
    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        while (running) {
            try {
                if (!mempoolQueue.isEmpty()) processTransactions();
                Thread.sleep(MempoolConfig.PROCESS_INTERVAL_MS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                Log.error(getClass(), "MempoolService interrupted", e);
            } catch (Exception e) {
                Log.error(getClass(), "Error in MempoolService loop", e);
            }
        }
    }
}
