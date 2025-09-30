package util;

import java.io.File;
import java.time.LocalDateTime;
import java.util.logging.*;

public class Log {

    private static Logger logger;
    private static boolean initialized = false;

    private static final String LOG_DIR = "logs";
    private static final String LOG_FILE_PREFIX = "app";
    private static final String LOG_FILE_EXTENSION = ".log";
    private static final boolean APPEND = false; // Overwrite the log file each time


    private Log() { }

    private static synchronized Logger getLogger() {
        if (initialized) return logger;

        logger = Logger.getLogger("AppLogger");
        logger.setUseParentHandlers(false); // Disable console logging

        for (Handler h : logger.getHandlers()) {
            logger.removeHandler(h);
        }

        try {
            File dir = new File("logs");
            if (!dir.exists()) dir.mkdirs();

            FileHandler fh = new FileHandler(
                    LOG_DIR + File.separator + LOG_FILE_PREFIX + "_" +
                    LocalDateTime.now().toString().replace(":", "-") + LOG_FILE_EXTENSION,
                    APPEND
            );
            fh.setFormatter(new SimpleFormatter() {
                @Override
                public synchronized String format(LogRecord logRecord) {
                    return String.format("[%1$tF %1$tT] [%2$s] [%3$s] %4$s%n",
                            logRecord.getMillis(),
                            logRecord.getLevel(),
                            logRecord.getLoggerName(),
                            logRecord.getMessage()
                    );
                }
            });

            logger.addHandler(fh);
            initialized = true;

        } catch (Exception e) {
            logger.warning("Failed to initialize logger: " + e.getMessage());
        }

        return logger;
    }

    public static void debug(Class<?> c, String msg) {
        getLogger().fine("[" + c.getSimpleName() + "] " + msg);
    }

    public static void info(Class<?> c, String msg) {
        getLogger().info("[" + c.getSimpleName() + "] " + msg);
    }

    public static void warn(Class<?> c, String msg) {
        getLogger().warning("[" + c.getSimpleName() + "] " + msg);
    }

    public static void error(Class<?> c, String msg) {
        getLogger().severe("[" + c.getSimpleName() + "] " + msg);
    }

    public static void error(Class<?> c, String msg, Throwable t) {
        LogRecord rec = new LogRecord(Level.SEVERE, "[" + c.getSimpleName() + "] " + msg);
        rec.setThrown(t);
        getLogger().log(rec);
    }
}
