package exceptions;

public class FailedToRegisterMenuException extends RuntimeException {
    public FailedToRegisterMenuException(String message) {
        super(message);
    }
    public FailedToRegisterMenuException(String message, Throwable cause) {
        super(message, cause);
    }
    public FailedToRegisterMenuException(Throwable cause) {
        super(cause);
    }
}
