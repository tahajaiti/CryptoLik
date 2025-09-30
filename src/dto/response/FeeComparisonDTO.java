package dto.response;

import entity.enums.FeePriority;

public class FeeComparisonDTO {
    private final FeePriority priority;
    private final int minPosition;
    private final int maxPosition;
    private final double avgFee;
    private final String speed;
    private final int estTimeSeconds;

    public FeeComparisonDTO(FeePriority priority, int minPosition, int maxPosition,
                            double avgFee, String speed, int estTimeSeconds) {
        this.priority = priority;
        this.minPosition = minPosition;
        this.maxPosition = maxPosition;
        this.avgFee = avgFee;
        this.speed = speed;
        this.estTimeSeconds = estTimeSeconds;
    }

    public FeePriority getPriority() { return priority; }
    public int getMinPosition() { return minPosition; }
    public int getMaxPosition() { return maxPosition; }
    public double getAvgFee() { return avgFee; }
    public String getSpeed() { return speed; }
    public int getEstTimeSeconds() { return estTimeSeconds; }
}
