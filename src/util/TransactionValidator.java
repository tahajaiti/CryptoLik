package service;

import entity.Transaction;
import entity.Wallet;
import entity.enums.WalletType;

import java.util.regex.Pattern;

public class TransactionValidator {

    private static final Pattern ETH_ADDRESS_PATTERN = Pattern.compile("^0x[a-fA-F0-9]{40}$");

    private static final Pattern BTC_ADDRESS_PATTERN = Pattern.compile("^(1|3|bc1)[a-zA-Z0-9]{20,}$");

    public static void validate(Transaction tx, Wallet sourceWallet) throws IllegalArgumentException {
        if (tx.getAmount() <= 0) {
            throw new IllegalArgumentException("The amount must be positive.");
        }

        if (sourceWallet.getBalance() < (tx.getAmount() + tx.getFee())) {
            throw new IllegalArgumentException("No funds available in the wallet.");
        }

        if (!isValidAddress(tx.getDestAddress(), sourceWallet.getWalletType())) {
            throw new IllegalArgumentException("Invalid destination address.");
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
