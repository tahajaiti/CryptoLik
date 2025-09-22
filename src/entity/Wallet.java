package entity;

import entity.enums.WalletType;

public abstract class Wallet {
    protected int id;
    protected WalletType walletType;
    protected double balance;
    protected String address;

    public Wallet(int id, WalletType walletType, double balance) {
        this.id = id;
        this.walletType = walletType;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public WalletType getWalletType() {
        return walletType;
    }

    public void setWalletType(WalletType walletType) {
        this.walletType = walletType;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
