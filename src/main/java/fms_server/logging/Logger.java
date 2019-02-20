package fms_server.logging;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Provides an abstraction to System.out.print that will format the output for better logging
 * TODO Add functionality to record logs
 */
public class Logger {
    /**
     * Log levels, logs can be filtered by levels
     */
    public enum LEVEL {INFO, PASS, FAIL, HEAD ,WARN, ERROR}

    /**
     * Color enums, all possible colors with ascii escape codes
     */
    public enum COLOR {WHITE, RED, GREEN, YELLOW, BLUE, MAGENTA, CYAN, BLACK, NULL}

    /**
     * Formatting enum, lets you do a bunch of cool formats
     */
    public enum FORMATS {ITALIC, BOLD, RESET, UNDERLINE, STRIKETHROUGH}

    /**
     * This is the level that the logs are filtered by
     */
    private static LEVEL logLevel = LEVEL.INFO;

    /**
     * Most basic log command
     *
     * @param level   level of log
     * @param message message to display
     */
    public static void log(LEVEL level, String message) {
        log(level, message, true);
    }

    /**
     * Interprets each level to create a visually enticing output
     *
     * @param level   level
     * @param message message to display
     * @param fancy   fancy output or not
     */
    public static void log(LEVEL level, String message, boolean fancy) {
        if (!fancy) {
            log(level, message, COLOR.WHITE, COLOR.NULL, COLOR.NULL, COLOR.NULL);
            return;
        }
        switch (level) {
            case HEAD:
                log(level, message, COLOR.CYAN, COLOR.NULL, COLOR.CYAN, COLOR.NULL);
                break;
            case INFO:
                log(level, message, COLOR.NULL, COLOR.NULL, COLOR.NULL, COLOR.NULL);
                break;
            case WARN:
                log(level, message, COLOR.YELLOW, COLOR.NULL, COLOR.YELLOW, COLOR.NULL);
                break;
            case PASS:
                log(level, message, COLOR.NULL, COLOR.GREEN, COLOR.GREEN, COLOR.NULL);
                break;
            case FAIL:
                log(level, message, COLOR.NULL, COLOR.RED, COLOR.RED, COLOR.NULL);
                break;
            case ERROR:
                log(level, message, COLOR.RED, COLOR.NULL, COLOR.RED, COLOR.NULL);
                break;
        }
    }

    /**
     * Creates a log output that is colored
     *
     * @param level      the level of the log
     * @param message    message to display
     * @param fg_tag     color of the level tag e.g. [INFO]
     * @param bg_tag     background of level tag
     * @param fg_message message foreground color
     * @param bg_message message background color
     */
    public static void log(LEVEL level, String message, COLOR fg_tag, COLOR bg_tag, COLOR fg_message, COLOR bg_message) {
        if (level.ordinal() >= logLevel.ordinal()) {
            Calendar calendar = Calendar.getInstance();
            String timestamp = new SimpleDateFormat("yyyy/MM/dd - HH:mm:ss:mmm").format(calendar.getTime());
            System.out.println(format(fg_message, bg_message) + "[" + timestamp + "] " + format(fg_tag, bg_tag, FORMATS.BOLD) + "[" + level.name() + "]" + format(fg_message, bg_message) + ": " + message + format(COLOR.NULL, COLOR.NULL));
        }
    }

    public static void info(String message) {
        log(LEVEL.INFO, message);
    }

    public static void warn(String message) {
        log(LEVEL.WARN, message);
    }

    public static void error(String message) {
        log(LEVEL.ERROR, message);
    }

    public static void pass(String message) {
        log(LEVEL.PASS, message);
    }

    public static void fail(String message) {
        log(LEVEL.FAIL, message);
    }

    public static void head(String message) {
        log(LEVEL.HEAD, message);
    }

    public static void line() {
        System.out.println(" ");
    }

    /**
     * Generates a string that with escape codes to apply a foreground and background color
     *
     * @param foreground The desired color
     * @param background background color
     * @return formatted escape string
     */
    public static String format(COLOR foreground, COLOR background, FORMATS... formats) {
        StringBuilder str = new StringBuilder("\u001B");
        switch (foreground) {
            case WHITE:
                str.append("[37");
                break;
            case RED:
                str.append("[31");
                break;
            case GREEN:
                str.append("[32");
                break;
            case YELLOW:
                str.append("[33");
                break;
            case BLUE:
                str.append("[34");
                break;
            case MAGENTA:
                str.append("[35");
                break;
            case CYAN:
                str.append("[36");
                break;
            case BLACK:
                str.append("[37");
                break;
            default:
                str.append("[0");
                break;
        }

        for (FORMATS format : formats) {
            str.append(';');
            switch (format) {
                case BOLD:
                    str.append('1');
                    break;
                case ITALIC:
                    str.append('3');
                    break;
                case UNDERLINE:
                    str.append('4');
                    break;
                case STRIKETHROUGH:
                    str.append('9');
                    break;
            }
        }
//        str.append("m");

//        str.append("\u001B");
        if (background != COLOR.NULL) {
            str.append(';');
            switch (background) {
                case WHITE:
                    str.append("47");
                    break;
                case RED:
                    str.append("41");
                    break;
                case GREEN:
                    str.append("42");
                    break;
                case YELLOW:
                    str.append("43");
                    break;
                case BLUE:
                    str.append("44");
                    break;
                case MAGENTA:
                    str.append("45");
                    break;
                case CYAN:
                    str.append("46");
                    break;
                case BLACK:
                    str.append("47");
                    break;
            }
        }
        str.append("m");
        return str.toString();
    }

    public static LEVEL getLogLevel() {
        return logLevel;
    }

    public static void setLogLevel(LEVEL logLevel) {
        Logger.logLevel = logLevel;
    }
}
