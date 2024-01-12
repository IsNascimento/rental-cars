package org.example.log;

public class Logger {

    private static Logger instance;

    private Logger() {
    }

    public static synchronized Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    public void info(String message) {
        System.out.println("INFO: " + message);
    }

    public void warning(String message) {
        System.out.println("WAR: " + message);
    }

    public void warning(Exception e) {
        System.out.println("WAR: " + e.getMessage());
        e.printStackTrace();
    }

    public void error(String message) {
        System.out.println("ERR: " + message);
    }

    public void error(Exception e) {
        System.out.println("ERR: " + e.getMessage());
        e.printStackTrace();
    }
}
