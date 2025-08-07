package com.aether.business.constaints;

public class Debug {
    public static final String BASE     = "<Debug> ";
    public static final String ERROR    = "<Debug> [ERROR] ";
    public static final String INFO     = "<Debug> [INFO] ";
    public static final String WARN     = "<Debug> [WARN] ";
    public static String USER           = "<User> ";

    public static void base(String string) {
        System.out.println(BASE + string);
    }

    public static void error(String string) {
        System.out.println(ERROR + string);
    }

    public static void info(String string) {
        System.out.println(INFO + string);
    }

    public static void warn(String string) {
        System.out.println(WARN + string);
    }

    public static void user(String string) {
        System.out.println(USER + string);
    }

    public static void user() {
        System.out.print(USER);
    }
}
