package com.aether.dto;

import com.aether.core.types.enums.DeviceStatus;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.UUID;

@JsonTypeName("Light")
public class LightDTO extends DeviceDTO{
    private final Integer deviceBrightness;
    private final Integer deviceColorTemperature;

    @JsonCreator
    public LightDTO(
            @JsonProperty("deviceUUID")             UUID deviceUUID,
            @JsonProperty("deviceName")             String deviceName,
            @JsonProperty("deviceLocation")         String deviceLocation,
            @JsonProperty("deviceStatus")           DeviceStatus deviceStatus,
            @JsonProperty("deviceBrightness")       Integer deviceBrightness,
            @JsonProperty("deviceColorTemperature") Integer deviceColorTemperature) {
        super(deviceUUID, deviceName, deviceLocation, deviceStatus);
        this.deviceBrightness = deviceBrightness;
        this.deviceColorTemperature = deviceColorTemperature;
    }

    @JsonGetter("deviceBrightness")
    public Integer getDeviceBrightness() {
        return deviceBrightness;
    }

    @JsonGetter("deviceColorTemperature")
    public Integer getDeviceColorTemperature() {
        return deviceColorTemperature;
    }
}
