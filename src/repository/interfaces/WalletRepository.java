package repository.interfaces;

import entity.Wallet;

import java.util.Optional;

public interface WalletRepository  extends Repository<entity.Wallet, Integer> {
    Optional<Wallet> findByAddress(String address);
}
