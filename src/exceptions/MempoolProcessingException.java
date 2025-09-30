package exceptions;

public class MempoolProcessingException extends MempoolException {
    public MempoolProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}