package me.yevgnenll.uniqueid.util;

public class Checks {

    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }
}
