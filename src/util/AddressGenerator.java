package util;

import entity.enums.WalletType;

import java.security.SecureRandom;

public class AddressGenerator {
    private static final String BASE58 = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz";
    private static final SecureRandom random = new SecureRandom();

    public static String generateAddress(WalletType type){
        if(type == WalletType.BITCOIN){
            return generateBitcoinAddress();
        } else if (type == WalletType.ETHEREUM) {
            return generateEthereumAddress();
        } else {
            throw new IllegalArgumentException("Unsupported wallet type: " + type);
        }
    }

    private static String generateBitcoinAddress(){
        StringBuilder address = new StringBuilder("1");
        for (int i = 0; i < 33; i++) {
            int index = random.nextInt(BASE58.length());
            address.append(BASE58.charAt(index));
        }

        int choice = random.nextInt(3);
        if (choice == 0) {
            address.setCharAt(0, '1');
        } else if (choice == 1) {
            address.setCharAt(0, '3');
        } else {
            address.setCharAt(0, 'b');
            address.insert(1, 'c');
        }

        return address.toString();
    }

    private static String generateEthereumAddress(){
        StringBuilder address = new StringBuilder("0x");
        for (int i = 0; i < 40; i++) {
            int index = random.nextInt(16);
            address.append(Integer.toHexString(index));
        }
        return address.toString();
    }
}
