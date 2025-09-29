package util;

import entity.Transaction;
import entity.Wallet;
import entity.enums.FeePriority;
import entity.enums.WalletType;

import java.util.regex.Pattern;

public class TransactionValidator {

    private static final Pattern ETH_ADDRESS_PATTERN = Pattern.compile("^0x[a-fA-F0-9]{40}$");

    private static final Pattern BTC_ADDRESS_PATTERN = Pattern.compile("^(1|3|bc1)[a-zA-Z0-9]{20,}$");

    private TransactionValidator(){}

    public static FeePriority parseFeePriority(String input) {
        if (input == null) throw new IllegalArgumentException("Fee priority is required.");
        input = input.trim();
        switch (input) {
            case "1":
            case "economic":
            case "ECONOMIC":
                return FeePriority.ECONOMIC;
            case "2":
            case "standard":
            case "STANDARD":
                return FeePriority.STANDARD;
            case "3":
            case "rapid":
            case "RAPID":
                return FeePriority.RAPID;
            default:
                throw new IllegalArgumentException("Invalid fee priority selection.");
        }
    }

    private static boolean isValidAddress(String address, WalletType type) {
        if (type == WalletType.BITCOIN) {
            return BTC_ADDRESS_PATTERN.matcher(address).matches();
        } else if (type == WalletType.ETHEREUM) {
            return ETH_ADDRESS_PATTERN.matcher(address).matches();
        }
        return false;
    }
}
