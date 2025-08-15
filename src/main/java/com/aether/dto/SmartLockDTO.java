package com.aether.dto;

import com.aether.core.types.enums.DeviceStatus;
import com.aether.core.types.enums.LockStatus;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.UUID;

@JsonTypeName("SmartLock")
public class SmartLockDTO extends DeviceDTO {
    private final LockStatus deviceLockStatus;

    @JsonCreator
    public SmartLockDTO(
            @JsonProperty("deviceUUID") UUID deviceUUID,
            @JsonProperty("deviceName")     String deviceName,
            @JsonProperty("deviceLocation") String deviceLocation,
            @JsonProperty("deviceStatus") DeviceStatus deviceStatus,
            @JsonProperty("deviceLockStatus") LockStatus deviceLockStatus) {
        super(deviceUUID, deviceName, deviceLocation, deviceStatus);
        this.deviceLockStatus = deviceLockStatus;
    }

    @JsonGetter("deviceLockStatus")
    public LockStatus getDeviceLockStatus() {
        return deviceLockStatus;
    }
}
