package service.interfaces;

import dto.response.FeeComparisonDTO;
import dto.response.MempoolPositionResponseDTO;
import dto.response.MempoolStateResponseDTO;
import dto.response.WalletResponseDTO;
import entity.Transaction;

import java.util.List;

public interface MempoolService extends Runnable{
    void addTransaction(Transaction tx);

    MempoolPositionResponseDTO getPosition(String txId);

    List<FeeComparisonDTO> compareFeeLevels();

    MempoolStateResponseDTO getCurrentMempoolState(WalletResponseDTO userWallet);

    void stop();
}
