package service.impl;

import dto.response.WalletResponseDTO;
import entity.enums.WalletType;
import service.interfaces.AuthService;

public class WalletDisplayService {
    
    private final AuthService authService;

    public WalletDisplayService(AuthService authService) {
        this.authService = authService;
    }

    public String getWalletDetails() {
        WalletResponseDTO wallet = authService.getCurrentUser();

        StringBuilder sb = new StringBuilder();
        sb.append("===== Wallet Details =====\n");
        sb.append("Wallet ID: ").append(wallet.getId()).append("\n");
        sb.append("Address: ").append(wallet.getAddress()).append("\n");
        sb.append("Wallet Type: ").append(wallet.getWalletType().name()).append("\n");

        if (wallet.getWalletType() == WalletType.BITCOIN) {
            sb.append("Balance: ").append(wallet.getBalance()).append(" BTC\n");
        } else if (wallet.getWalletType() == WalletType.ETHEREUM) {
            sb.append("Balance: ").append(wallet.getBalance()).append(" ETH\n");
        }

        return sb.toString();
    }
}
