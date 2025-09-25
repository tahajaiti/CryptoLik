package service.impl;

import config.FeeConfig;
import entity.Wallet;
import entity.enums.FeePriority;
import service.interfaces.FeeCalculator;

public class EthereumFeeCalculatorImpl implements FeeCalculator {

    @Override
    public double calculateFee(double amount, FeePriority priority, Wallet wallet) {
        int gasLimit = 21000;

        int gasPrice;
        switch (priority) {
            case ECONOMIC: gasPrice = 5; break;
            case STANDARD: gasPrice = 20; break;
            case RAPID: gasPrice = 50; break;
            default: gasPrice = 10;
        }

        double fee = (double) gasLimit * gasPrice;
        double ethPrice = FeeConfig.ETHEREUM_PRICE;
        return fee * ethPrice;
    }
}
