package dto.request;

import entity.enums.FeePriority;
import entity.enums.WalletType;

public class TransactionRequestDTO {
    private String srcAddress;
    private String destAddress;
    private double amount;
    private String feePriority;
    private WalletType walletType;

    public TransactionRequestDTO(String srcAddress, String destAddress, double amount, String feePriority, WalletType walletType) {
        this.srcAddress = srcAddress;
        this.destAddress = destAddress;
        this.amount = amount;
        this.feePriority = feePriority;
        this.walletType = walletType;
    }

    public String getFeePriority() {
        return feePriority;
    }

    public WalletType getWalletType() {
        return walletType;
    }
    

    public String getSrcAddress() {
        return srcAddress;
    }

    public String getDestAddress() {
        return destAddress;
    }


    public double getAmount() {
        return amount;
    }

}
