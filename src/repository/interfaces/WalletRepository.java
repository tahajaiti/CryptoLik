package repository;

import entity.Wallet;

import java.util.List;
import java.util.Optional;

public interface WalletRepository {
    void save(Wallet wallet);
    Optional<Wallet> findById(int id);
    Optional<Wallet> findByAddress(String address);
    List<Wallet> findAll();
    void update(Wallet wallet);
    void delete(int id);
}
