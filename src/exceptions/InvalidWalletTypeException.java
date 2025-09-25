package exceptions;

public class InvalidWalletTypeException extends RuntimeException {
    public InvalidWalletTypeException(String message) {
        super(message);
    }
}
