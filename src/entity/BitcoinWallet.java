package entity;

import entity.enums.WalletType;

public class BitcoinWallet extends Wallet{

    public BitcoinWallet(double balance, String address) {
        super(WalletType.BITCOIN, balance, address);
    }

}
