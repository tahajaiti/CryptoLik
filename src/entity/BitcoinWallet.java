package entity;

import entity.enums.WalletType;

public class BitcoinWallet extends Wallet{

    public BitcoinWallet(int id, double balance) {
        super(id, WalletType.BITCOIN, balance);
    }

}
