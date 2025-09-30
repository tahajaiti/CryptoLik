package dto.response;

public class MempoolPositionResponseDTO {
    private final int position;
    private final int total;
    private final int estimation;

    public MempoolPositionResponseDTO(int position, int total, int estimation) {
        this.position = position;
        this.total = total;
        this.estimation = estimation;
    }

    public int getPosition() {
        return position;
    }

    public int getTotal() {
        return total;
    }

    public int getEstimation() {
        return estimation;
    }
}
