package dto.request;

import entity.enums.WalletType;

public class WalletRequestDTO {
    private WalletType walletType;
    private String password;

    public WalletRequestDTO() {}

    public WalletRequestDTO(WalletType walletType, String password) {
        this.walletType = walletType;
        this.password = password;
    }

    public WalletType getWalletType() {
        return walletType;
    }

    public void setWalletType(WalletType walletType) {
        this.walletType = walletType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
