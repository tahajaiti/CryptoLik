package service.interfaces;


public interface SessionService {

    String startSession(int id);

    void endSession(String token);

    int getWalletId(String token);

    boolean isValid(String token);

}
