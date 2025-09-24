package dto.response;

public class AuthResponseDTO {
    private WalletResponseDTO wallet;
    private String token;

    public AuthResponseDTO(WalletResponseDTO wallet, String token) {
        this.wallet = wallet;
        this.token = token;
    }

    public WalletResponseDTO getWallet() {
        return wallet;
    }

    public String getToken() {
        return token;
    }
}
