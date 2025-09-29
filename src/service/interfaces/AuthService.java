package service.interfaces;

import dto.request.AuthRequestDTO;
import dto.request.WalletRequestDTO;
import dto.response.AuthResponseDTO;
import dto.response.WalletResponseDTO;

public interface AuthService {
    WalletResponseDTO register(WalletRequestDTO requestDTO);
    AuthResponseDTO login(AuthRequestDTO requestDTO);
    void logout();
    WalletResponseDTO getCurrentUser();
}
