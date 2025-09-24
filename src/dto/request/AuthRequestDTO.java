package dto.request;

import entity.enums.WalletType;

public class AuthRequestDTO {
    private final int id;
    private final String password;

    public AuthRequestDTO(int id, String password) {
        this.id = id;
        this.password = password;
    }

    public int getId() {
        return id;
    }
    public String getPassword() {
        return password;
    }
}
