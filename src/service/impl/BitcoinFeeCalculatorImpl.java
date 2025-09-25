package service.impl;

import config.FeeConfig;
import entity.Wallet;
import entity.enums.FeePriority;
import service.interfaces.FeeCalculator;

public class BitcoinFeeCalculatorImpl implements FeeCalculator {
    @Override
    public double calculateFee(double amount, FeePriority priority, Wallet wallet) {
        int txSizeBytes = 250;

        int satPerByte;
        switch (priority) {
            case ECONOMIC: satPerByte = 5; break;
            case STANDARD: satPerByte = 20; break;
            case RAPID: satPerByte = 50; break;
            default: satPerByte = 10;
        }

        double fee = txSizeBytes * satPerByte;
        double btcPrice = FeeConfig.BITCOIN_PRICE;
        return fee * btcPrice;
    }
}
