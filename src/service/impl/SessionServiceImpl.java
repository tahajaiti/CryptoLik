package service.impl;

import service.interfaces.SessionService;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SessionServiceImpl implements SessionService {

    private static final Map<String, Integer> sessions = new HashMap<>();

    public String startSession(int id){
        String token = UUID.randomUUID().toString();
        sessions.put(token, id);
        return token;
    }

    public void endSession(String token){
        sessions.remove(token);
    }

    public int getWalletId(String token){
        return sessions.get(token);
    }

    public boolean isValid(String token){
        return sessions.containsKey(token);
    }
}
