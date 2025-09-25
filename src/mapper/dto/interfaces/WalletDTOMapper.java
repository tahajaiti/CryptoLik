package mapper.dto.interfaces;

import dto.request.WalletRequestDTO;
import dto.response.WalletResponseDTO;
import entity.Wallet;

public interface WalletDTOMapper extends DTOMapper<Wallet, WalletResponseDTO, WalletRequestDTO> {
    
}
