package exceptions;

public class TransactionNotFoundException extends MempoolException {
    public TransactionNotFoundException(String txId) {
        super("Transaction not found in mempool: " + txId);
    }
}