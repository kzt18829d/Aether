package com.aether.business.constaints;

public class Terminal {
    public static final String BASE     = "<Aether> ";
    public static final String ERROR    = "<Aether> [ERROR] ";
    public static final String INFO     = "<Aether> [INFO] ";
    public static final String WARN     = "<Aether> [WARN] ";
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
