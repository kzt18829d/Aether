package com.aether.dto;

import com.aether.core.types.enums.DeviceStatus;
import com.aether.core.types.enums.NightVisionStatus;
import com.aether.core.types.enums.RecordingStatus;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.UUID;

@JsonTypeName("SecurityCamera")
public class SecurityCameraDTO extends DeviceDTO{
    private final RecordingStatus deviceRecordingStatus;
    private final NightVisionStatus deviceNightVisionStatus;

    @JsonCreator
    public SecurityCameraDTO(
            @JsonProperty("deviceUUID") UUID deviceUUID,
            @JsonProperty("deviceName")     String deviceName,
            @JsonProperty("deviceLocation") String deviceLocation,
            @JsonProperty("deviceStatus") DeviceStatus deviceStatus,
            @JsonProperty("deviceRecordingStatus") RecordingStatus deviceRecordingStatus,
            @JsonProperty("deviceNightVisionStatus") NightVisionStatus deviceNightVisionStatus) {
        super(deviceUUID, deviceName, deviceLocation, deviceStatus);
        this.deviceNightVisionStatus = deviceNightVisionStatus;
        this.deviceRecordingStatus = deviceRecordingStatus;
    }

    @JsonGetter("deviceRecordingStatus")
    public RecordingStatus getDeviceRecordingStatus() {
        return deviceRecordingStatus;
    }

    @JsonGetter("deviceNightVisionStatus")
    public NightVisionStatus getDeviceNightVisionStatus() {
        return deviceNightVisionStatus;
    }
}
