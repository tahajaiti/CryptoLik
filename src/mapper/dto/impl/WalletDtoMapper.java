package mapper.dto.impl;

import dto.request.WalletRequestDTO;
import dto.response.WalletResponseDTO;
import entity.Wallet;
import mapper.dto.interfaces.WalletDTOMapper;

public class WalletDtoMapper implements WalletDTOMapper {

    @Override
    public WalletResponseDTO toResponseDTO(Wallet wallet) {
        return new WalletResponseDTO(wallet.getId(),wallet.getAddress(), wallet.getWalletType(), wallet.getBalance());
    }

    @Override
    public void updateFromRequest(Wallet wallet, WalletRequestDTO requestDTO) {
        // wallet.setWalletType(requestDTO.getWalletType());
        wallet.setPassword(requestDTO.getPassword());
    }

}
