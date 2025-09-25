package util;

import entity.enums.WalletType;
import exceptions.InvalidWalletTypeException;

public class WalletValidator {

    private WalletValidator() {
    }

    public static WalletType parseWalletType(String input) {
        if (input == null) throw new InvalidWalletTypeException("Wallet type is required.");
        input = input.trim();
        switch (input) {
            case "1":
            case "bitcoin":
            case "Bitcoin":
                return WalletType.BITCOIN;
            case "2":
            case "ethereum":
            case "Ethereum":
                return WalletType.ETHEREUM;
            default:
                throw new InvalidWalletTypeException("Invalid wallet type selection.");
        }
    }

    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= 6;
    }

}
