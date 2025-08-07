package com.aether.business.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Enum-класс, содержащий статусы устройств
 */

public enum DeviceStatus {
    ONLINE,
    OFFLINE,
    ERROR;

    @JsonCreator
    public static DeviceStatus fromString(String status) {
        return switch (status) {
            case "online", "ONLINE" -> ONLINE;
            case "error", "ERROR" -> ERROR;
            default -> OFFLINE;
        };
    }
}
