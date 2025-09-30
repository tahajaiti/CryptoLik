package exceptions;

public class MempoolException extends RuntimeException {
    public MempoolException(String message) {
        super(message);
    }

    public MempoolException(String message, Throwable cause) {
        super(message, cause);
    }
}
