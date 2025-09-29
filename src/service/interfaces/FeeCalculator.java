package service.interfaces;

import entity.enums.FeePriority;

public interface FeeCalculator {
    double calculateFee(FeePriority priority);
}
