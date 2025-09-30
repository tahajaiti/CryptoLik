package ui.menu.sub;

import dto.response.MempoolStateResponseDTO;
import dto.response.WalletResponseDTO;
import service.interfaces.AuthService;
import service.interfaces.MempoolService;
import ui.Menu;
import ui.UIManager;
import ui.menu.MenuResult;

public class MempoolStateMenu implements Menu {
    private static final String ID = "mempool_state_menu";
    private final MempoolService mempoolService;
    private final AuthService authService;

    public MempoolStateMenu(MempoolService mempoolService, AuthService authService) {
        this.authService = authService;
        this.mempoolService = mempoolService;
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
    public MenuResult render(UIManager ui) {
        WalletResponseDTO wallet = authService.getCurrentUser();

        MempoolStateResponseDTO state = mempoolService.getCurrentMempoolState(wallet);

        ui.showL("=== MEMPOOL STATE ===");
        ui.showL("Pending transactions: " + state.getTotalTransactions());
        ui.showL("┌──────────────────────────────────┬────────┐");
        ui.showL("│ Transaction                      │ Fee    │");
        ui.showL("├──────────────────────────────────┼────────┤");

        for (MempoolStateResponseDTO.TransactionEntry entry : state.getTransactions()) {
            String label;
            if (entry.isUserTx()) {
                label = ">>> YOUR TX: " + entry.getTxHash();
            } else {
                label = entry.getTxHash() + " (anonymous)";
            }

            ui.showL(String.format("│ %-32s │ %6.2f │", label, entry.getFee()));
        }

        ui.showL("└──────────────────────────────────┴────────┘");

        return MenuResult.goTo("main_menu");
    }
}
