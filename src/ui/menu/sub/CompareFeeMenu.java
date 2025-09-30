package ui.menu.sub;

import dto.response.FeeComparisonDTO;
import service.impl.MempoolServiceImpl;
import service.interfaces.MempoolService;
import ui.Menu;
import ui.menu.MenuResult;
import ui.UIManager;

import java.util.List;

public class CompareFeeMenu implements Menu {

    private final String ID = "compare_fee_menu";
    private final MempoolService mempoolService;

    public CompareFeeMenu(MempoolService mempoolService) {
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
        List<FeeComparisonDTO> comparisons = mempoolService.compareFeeLevels();

        if (comparisons.isEmpty()) {
            ui.showL("No transactions in the mempool to compare.");
            return MenuResult.goTo("main_menu");
        }

        String border = "+------------+-------------+-------------+-------------+-------------+";
        String header = String.format("| %-10s | %-11s | %-11s | %-11s | %-11s |",
                "Priority", "Min Pos", "Max Pos", "Avg Fee", "ETA (s)");

        ui.showL(border);
        ui.showL(header);
        ui.showL(border);

        for (FeeComparisonDTO dto : comparisons) {
            ui.showL(String.format("| %-10s | %-11d | %-11d | %-11.5f | %-11d |",
                    dto.getPriority().name(),
                    dto.getMinPosition(),
                    dto.getMaxPosition(),
                    dto.getAvgFee(),
                    dto.getEstTimeSeconds()));
        }

        ui.showL(border);
        return MenuResult.goTo("main_menu");
    }
}
