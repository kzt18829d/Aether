package com.aether.business.enums;

/**
 * Enum-класс, содержащий статусы устройств
 */

public enum DeviceStatus {
    ONLINE,
    OFFLINE,
    ERROR

//    @JsonCreator
//    public static DeviceStatus fromString(String status) {
//        return switch (status) {
//            case "online", "ONLINE" -> ONLINE;
//            case "error", "ERROR" -> ERROR;
//            default -> OFFLINE;
//        };
//    }
}
