package com.aether.business.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

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
        if (bool) return LOCKED;
        else return UNLOCKED;
    }

    public static boolean getBoolean(LockStatus lockStatus) {
        return switch (lockStatus) {
            case LOCKED -> true;
            case UNLOCKED -> false;
        };
    }

    @JsonCreator
    public static LockStatus fromString(String status) {
        if ("LOCKED".equals(status)) return LOCKED;
        else return UNLOCKED;
    }
}
