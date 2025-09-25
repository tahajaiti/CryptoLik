package exceptions;

public class FailedToSaveException extends RuntimeException {
    public FailedToSaveException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
