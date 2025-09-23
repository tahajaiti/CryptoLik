package util;

public class PasswordUtil {

    public static String hashPassword(String password) {
        return Integer.toHexString(password.hashCode());
    }
    public static boolean verifyPassword(String password, String hashedPassword) {
        return hashPassword(password).equals(hashedPassword);
    }
}
