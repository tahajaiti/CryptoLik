package util;

import entity.enums.WalletType;

import java.security.SecureRandom;

public class AddressGenerator {
    private static final String BASE58 = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz";
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generateAddress(WalletType type){
        switch (type){
            case BITCOIN: return generateBitcoinAddress();
            case ETHEREUM: return generateEthereumAddress();
            default: throw new IllegalArgumentException("Unsupported wallet type: " + type);
        }
    }

    private static String generateBitcoinAddress(){
        StringBuilder address = new StringBuilder();
        int choice = RANDOM.nextInt(3);

        if (choice == 0) {
            address.append('1');
        } else if (choice == 1) {
            address.append('3');
        } else {
            address.append("bc1");
        }

        int length = (choice == 2 ? 32 : 33);
        for (int i = 0; i < length; i++) {
            address.append(BASE58.charAt(RANDOM.nextInt(BASE58.length())));
        }
        return address.toString();
    }

    private static String generateEthereumAddress(){
        StringBuilder address = new StringBuilder("0x");
        for (int i = 0; i < 40; i++) {
            address.append(Integer.toHexString(RANDOM.nextInt(16)));
        }
        return address.toString();
    }
}
