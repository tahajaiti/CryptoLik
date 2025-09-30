package ui.menu.sub;

import dto.response.MempoolPositionResponseDTO;
import service.impl.MempoolServiceImpl;
import service.interfaces.MempoolService;
import ui.Menu;
import ui.menu.MenuResult;

public class TxPositionMenu implements Menu {
    private static final String ID = "tx_position_menu";
    private final MempoolService mempoolService;

    public TxPositionMenu(MempoolService mempoolService) {
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
    public MenuResult render(ui.UIManager ui) {
        String txId = ui.getString("Enter Transaction ID: ").trim();

        MempoolPositionResponseDTO resp = mempoolService.getPosition(txId);
        if (resp.getPosition() == -1) {
            ui.showL("Transaction not found in mempool.");
        } else {
            ui.showL("Transaction position: " + resp.getPosition() +
                    " of " + resp.getTotal() +
                    " | ETA: ~" + resp.getEstimation() + "s");
        }


        return MenuResult.goTo("main_menu");
    }
}
