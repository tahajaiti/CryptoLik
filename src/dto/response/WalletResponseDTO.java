package dto.response;

import entity.enums.WalletType;

public class WalletResponseDTO {
    private int id;
    private String address;
    private WalletType walletType;
    private double balance;

    public WalletResponseDTO() {}

    public WalletResponseDTO(int id, String address, WalletType walletType, double balance) {
        this.id = id;
        this.address = address;
        this.walletType = walletType;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
}
