package util;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Log {
    

    private Log() {

    }

    private static Logger logger(Class<?> c) {
        return Logger.getLogger(c.getName());
    }

    public static void debug(Class<?> c, String msg) {
        Logger l = logger(c);
        if (l.isLoggable(Level.FINE)) l.fine(msg);
    }

    public static void info(Class<?> c, String msg) {
        logger(c).info(msg);
    }

    public static void warn(Class<?> c, String msg) {
        logger(c).warning(msg);
    }

    public static void error(Class<?> c, String msg) {
        logger(c).severe(msg);
    }

    public static void error(Class<?> c, String msg, Throwable t) {
        logger(c).log(Level.SEVERE, msg, t);
    }
}
