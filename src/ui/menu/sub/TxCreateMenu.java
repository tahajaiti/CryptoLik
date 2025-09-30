package ui.menu.sub;

import dto.request.TransactionRequestDTO;
import dto.response.TransactionResponseDTO;
import dto.response.WalletResponseDTO;
import entity.enums.WalletType;
import service.interfaces.AuthService;
import service.interfaces.TransactionService;
import ui.Menu;
import ui.menu.MenuResult;

public class TxCreateMenu implements Menu {
    private static final String ID = "tx_create_menu";
    private final TransactionService txService;
    private final AuthService authService;

    public TxCreateMenu(TransactionService txService, AuthService authService) {
        this.txService = txService;
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
        WalletResponseDTO wallet = authService.getCurrentUser();

        String destAddress = ui.getString("Enter destination address: ").trim();
        double amount = ui.getDouble("Enter amount to send: ");
        String feePriorityInput = ui.getString("Enter fee priority (ECONOMIC, STANDARD, RAPID): ").trim();
        WalletType walletType = wallet.getWalletType();

        TransactionRequestDTO requestDTO = new TransactionRequestDTO(
                wallet.getAddress(),
                destAddress,
                amount,
                feePriorityInput,
                walletType
        );

        TransactionResponseDTO response = txService.createTransaction(requestDTO);

        ui.showL("Transaction created successfully!");
        ui.showL("Transaction ID: " + response.getId());
        ui.showL("Transaction Status: " + response.getStatus());

        return MenuResult.goTo("main_menu");
    }


}
