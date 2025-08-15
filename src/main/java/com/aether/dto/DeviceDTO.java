package com.aether.dto;

import com.aether.core.types.enums.DeviceStatus;
import com.fasterxml.jackson.annotation.*;

import java.util.UUID;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "deviceType",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = LightDTO.class, name = "Light"),
        @JsonSubTypes.Type(value = SecurityCameraDTO.class, name = "SecurityCamera"),
        @JsonSubTypes.Type(value = SmartLockDTO.class, name = "SmartLock"),
        @JsonSubTypes.Type(value = ThermostatDTO.class, name = "Thermostat")
})
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class DeviceDTO {
    private final UUID deviceUUID;
    private final String deviceName;
    private final String deviceLocation;
    private final DeviceStatus deviceStatus;

    @JsonCreator
    public DeviceDTO(
            @JsonProperty("deviceUUID")     UUID deviceUUID,
            @JsonProperty("deviceName")     String deviceName,
            @JsonProperty("deviceLocation") String deviceLocation,
            @JsonProperty("deviceStatus")   DeviceStatus deviceStatus) {
        this.deviceUUID = deviceUUID;
        this.deviceName = deviceName;
        this.deviceLocation = deviceLocation;
        this.deviceStatus = deviceStatus;
    }

    @JsonGetter("deviceUUID")
    public UUID getDeviceUUID() {
        return deviceUUID;
    }

    @JsonGetter("deviceName")
    public String getDeviceName() {
        return deviceName;
    }

    @JsonGetter("deviceLocation")
    public String getDeviceLocation() {
        return deviceLocation;
    }

    @JsonGetter("deviceStatus")
    public DeviceStatus getDeviceStatus() {
        return deviceStatus;
    }
}
