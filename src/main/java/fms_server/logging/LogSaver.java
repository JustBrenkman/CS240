/*
 * Copyright (c) 2019.
 * @author Ben Brenkman
 * Last Modified 3/4/19 11:06 AM
 */

package fms_server.logging;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.Calendar;
import java.util.Deque;

public class LogSaver {
    /**
     * Is in the process of writing messages to file
     */
    private static boolean logging = false;
    /**
     * Path to file
     */
    private Path path;
    /**
     * List of logs to write to file
     */
    private Deque<String> messageQueue;
    /**
     * Date that log file was created
     */
    private String timestamp;

    public LogSaver(String path) {
        this.path = Paths.get(path);
        messageQueue = new ArrayDeque<>();

        Calendar calendar = Calendar.getInstance();
        timestamp = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(calendar.getTime());
        File file = new File(path + "/" + timestamp + ".txt");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Converts an exception to a string list
     *
     * @param e exception
     * @return string reforestation
     */
    public static String exceptionStacktraceToString(Exception e) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        e.printStackTrace(ps);
        ps.close();
        return baos.toString();
    }

    /**
     * Log message
     * @param level
     * @param message
     */
    public void log(Logger.LEVEL level, String message) {
        log(level, message, null);
    }

    /**
     * Log message
     * @param level
     * @param message
     * @param e
     */
    public void log(Logger.LEVEL level, String message, Exception e) {
        Calendar calendar = Calendar.getInstance();
        String timestamp = new SimpleDateFormat("yyyy/MM/dd - HH:mm:ss:mmm").format(calendar.getTime());
        String log = "[" + timestamp + "] [" + level.name() + "]: " + message;

        messageQueue.add(log);
        if (e != null)
            messageQueue.add(exceptionStacktraceToString(e));

        if (messageQueue.size() > 10 && !logging)
            new Thread(this::writeLogQueueToFile).start();
    }

    /**
     * Write all logged messages to file
     */
    private void writeLogQueueToFile() {
        logging = true;
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path + "/" + timestamp + ".txt", true));
            PrintWriter printWriter = new PrintWriter(writer);
            while (!messageQueue.isEmpty())
                printWriter.println(messageQueue.pop());
            writer.close();
        } catch (IOException e) {
            Logger.warn("Missed a log, to much logging taking place");
        }
        logging = false;
    }

    /**
     * Flush out the queue
     */
    public void flush() {
        writeLogQueueToFile();
    }
}
