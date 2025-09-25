package service.impl;

import config.GlobalConfig;
import dto.request.AuthRequestDTO;
import dto.request.WalletRequestDTO;
import dto.response.AuthResponseDTO;
import dto.response.WalletResponseDTO;
import entity.BitcoinWallet;
import entity.EthereumWallet;
import entity.Wallet;
import entity.enums.WalletType;
import exceptions.InvalidCredentialsException;
import exceptions.InvalidPasswordException;
import exceptions.NotFoundException;
import mapper.dto.interfaces.WalletDTOMapper;
import repository.interfaces.WalletRepository;
import service.interfaces.AuthService;
import util.AddressGenerator;
import util.PasswordUtil;
import util.WalletValidator;

public class AuthServiceImpl implements AuthService {

    private final WalletRepository walletRepository;
    private final WalletDTOMapper walletMapper;
    private final SessionServiceImpl sessionService;

    public AuthServiceImpl(WalletRepository walletRepository,
            WalletDTOMapper walletMapper,
            SessionServiceImpl sessionService) {
        this.walletRepository = walletRepository;
        this.walletMapper = walletMapper;
        this.sessionService = sessionService;
    }

    @Override
    public WalletResponseDTO register(WalletRequestDTO dto) {
        if (!WalletValidator.isValidPassword(dto.getPassword())) {
            throw new InvalidPasswordException("Password must be at least 6 characters long.");
        }

        WalletType type = WalletValidator.parseWalletType(dto.getWalletType());

        String address = AddressGenerator.generateAddress(type);

        Wallet wallet = type == WalletType.BITCOIN
                ? new BitcoinWallet(GlobalConfig.DEFAULT_WALLET_BALANCE, address)
                : new EthereumWallet(GlobalConfig.DEFAULT_WALLET_BALANCE, address);

        wallet.setPassword(PasswordUtil.hashPassword(dto.getPassword()));

        Wallet savedWallet = walletRepository.save(wallet);

        return walletMapper.toResponseDTO(savedWallet);
    }

    @Override
    public AuthResponseDTO login(AuthRequestDTO dto) {
        Wallet wallet = walletRepository.findById(dto.getId())
                .orElseThrow(() -> new NotFoundException("Wallet not found"));

        if (!PasswordUtil.verifyPassword(dto.getPassword(), wallet.getPassword())) {
            throw new InvalidCredentialsException("Invalid credentials");
        }

        String token = sessionService.startSession(wallet.getId());
        return new AuthResponseDTO(walletMapper.toResponseDTO(wallet), token);
    }

    @Override
    public void logout(String token) {
        sessionService.endSession(token);
    }

    @Override
    public WalletResponseDTO getCurrentUser(String token) {
        int walletId = sessionService.getWalletId(token);
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> {
                    sessionService.endSession(token);
                    return new NotFoundException("Wallet not found");
                });
        return walletMapper.toResponseDTO(wallet);
    }

}
