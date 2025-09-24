package service.interfaces;

import java.util.UUID;

public interface SessionService {

    String startSession(int id);

    void endSession(String token);

    int getWalletId(String token);

    boolean isValid(String token);

}
