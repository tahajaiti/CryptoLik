package dto.request;


public class WalletRequestDTO {
    private String walletType;
    private String password;

    public WalletRequestDTO() {}

    public WalletRequestDTO(String walletType, String password) {
        this.walletType = walletType;
        this.password = password;
    }

    public String getWalletType() {
        return walletType;
    }

    public void setWalletType(String walletType) {
        this.walletType = walletType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
