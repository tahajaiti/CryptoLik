package config;

public class MempoolConfig {
    public static final int MAX_MEMPOOL_SIZE = 50;
    public static final int PROCESS_BATCH_SIZE = 3;
    public static final int PROCESS_INTERVAL_MS = 15000;
    public static final int MAX_GEN_TX = 15;

    private MempoolConfig() {
    }
}
