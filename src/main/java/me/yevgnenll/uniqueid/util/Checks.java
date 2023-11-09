package me.yevgnenll.uniqueid.util;

import lombok.NonNull;

public class Checks {

    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }

    public static <T extends @NonNull Object> T nonNull(T object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
        return object;
    }
}
