package entity;

import entity.enums.WalletType;

public class EthereumWallet  extends  Wallet{
    public EthereumWallet(double balance, String address) {
        super(WalletType.ETHEREUM, balance, address);
    }

}
