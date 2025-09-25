package mapper.DTO.impl;

import dto.request.WalletRequestDTO;
import dto.response.WalletResponseDTO;
import entity.Wallet;
import mapper.DTO.DTOMapper;

public class WalletDtoMapper implements DTOMapper<Wallet, WalletResponseDTO, WalletRequestDTO> {

    @Override
    public WalletResponseDTO toResponseDTO(Wallet wallet) {
        return new WalletResponseDTO(wallet.getAddress(), wallet.getWalletType(), wallet.getBalance());
    }

    @Override
    public void updateFromRequest(Wallet wallet, WalletRequestDTO requestDTO) {
        wallet.setWalletType(requestDTO.getWalletType());
        wallet.setPassword(requestDTO.getPassword());
    }

}
