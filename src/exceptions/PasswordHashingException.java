package exceptions;

public class PasswordHashingException extends RuntimeException {
    public PasswordHashingException(String message) { super(message); }
}
