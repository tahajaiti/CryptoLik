package entity;

import entity.enums.WalletType;

public class EthereumWallet  extends  Wallet{
    public EthereumWallet(int id, double balance) {
        super(id, WalletType.ETHEREUM, balance);
    }

}
