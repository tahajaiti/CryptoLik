package dto.response;

import java.util.List;

public class MempoolStateResponseDTO {
    private final int totalTransactions;
    private final List<TransactionEntry> transactions;

    public static class TransactionEntry {
        private final String txHash;
        private final double fee;
        private final boolean userTx;

        public TransactionEntry(String txHash, double fee, boolean userTx) {
            this.txHash = txHash;
            this.fee = fee;
            this.userTx = userTx;
        }

        public String getTxHash() { return txHash; }
        public double getFee() { return fee; }
        public boolean isUserTx() { return userTx; }
    }

    public MempoolStateResponseDTO(int totalTransactions, List<TransactionEntry> transactions) {
        this.totalTransactions = totalTransactions;
        this.transactions = transactions;
    }

    public int getTotalTransactions() { return totalTransactions; }
    public List<TransactionEntry> getTransactions() { return transactions; }
}
