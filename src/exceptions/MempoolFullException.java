package exceptions;

public class MempoolFullException extends MempoolException {
    public MempoolFullException() {
        super("Mempool has reached its max capacity");
    }
}