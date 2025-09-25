package ui.menu;

import java.util.logging.Logger;

import dto.request.AuthRequestDTO;
import dto.request.WalletRequestDTO;
import dto.response.AuthResponseDTO;
import dto.response.WalletResponseDTO;
import service.interfaces.AuthService;
import ui.Menu;

public class AuthMenu implements Menu {

    private static final String ID = "auth_menu";
    private final AuthService authService;
    private final Logger logger = Logger.getLogger(AuthMenu.class.getName());

    public AuthMenu(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public boolean canRender() {
        return true;
    }


    @Override
    public MenuResult render(ui.UIManager ui) {
        ui.showL("Authentication Menu:");
        ui.showL("1. Create new wallet");
        ui.showL("2. Login to existing wallet");
        ui.showL("3. Exit");
        String choice = ui.getString("> ").trim();

        switch (choice) {
            case "1": {
                String walletType = ui.getString("Wallet Type (Bitcoin, Ethereum): ").trim();
                String password = ui.getString("Password: ").trim();
                
                WalletRequestDTO requestDTO = new WalletRequestDTO(walletType, password);
                
                try {
                    WalletResponseDTO responseDTO = authService.register(requestDTO);
                    ui.showL(responseDTO.getWalletType() +" Wallet created successfully! Your Wallet ID: " + responseDTO.getId());
                } catch(Exception e){
                    logger.warning("Register failed: " + e.getMessage());
                    return MenuResult.stay();
                }

                return MenuResult.stay();
            }

            case "2": {
                int walletId = ui.getInt("Wallet ID: ");
                String password = ui.getString("Password: ").trim();
                
                AuthRequestDTO requestDTO = new AuthRequestDTO(walletId, password);
                
                try {
                    AuthResponseDTO responseDTO = authService.login(requestDTO);
                    ui.showL("Login successful! Welcome back, Wallet ID: " + responseDTO.getWallet().getId());
                } catch(Exception e){
                    logger.warning("Login failed: " + e.getMessage());
                    return MenuResult.stay();
                }

                return MenuResult.goTo("main_menu");
            }
            case "3":
                return MenuResult.exit();
            default:
                ui.showL("Invalid choice, please try again.");
                return MenuResult.stay();
        }
    }

}