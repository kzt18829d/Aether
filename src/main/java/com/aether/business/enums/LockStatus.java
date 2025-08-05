package com.aether.business.enums;

/**
 * ENUM статус блокировки
 */
public enum LockStatus {
    LOCKED,
    UNLOCKED;

    /**
     * Перевести статус в String
     * @param lockStatus
     * @return String
     */
    public static String getString(LockStatus lockStatus) {
        return switch (lockStatus) {
            case LOCKED -> "LOCKED";
            case UNLOCKED -> "UNLOCKED";
        };
    }

    /**
     * Установить статус из boolean
     * @param bool
     * @return LockStatus
     */
    public static LockStatus fromBoolean(boolean bool) {
        return switch (bool) {
            case true -> LOCKED;
            case false -> UNLOCKED;
        };
    }

    public static boolean getBoolean(LockStatus lockStatus) {
        return switch (lockStatus) {
            case LOCKED -> true;
            case UNLOCKED -> false;
        };
    }
}
