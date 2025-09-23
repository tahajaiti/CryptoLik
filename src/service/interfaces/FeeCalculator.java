package service.interfaces;

import entity.Wallet;
import entity.enums.FeePriority;

public interface FeeCalculator {
    double calculateFee(double amount, FeePriority priority, Wallet wallet);
}
