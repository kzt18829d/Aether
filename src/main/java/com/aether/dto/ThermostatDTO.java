package com.aether.dto;

import com.aether.core.types.enums.DeviceStatus;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.UUID;

@JsonTypeName("Thermostat")
public class ThermostatDTO extends DeviceDTO {
    private final Integer deviceTemperature;

    @JsonCreator
    public ThermostatDTO(
            @JsonProperty("deviceUUID") UUID deviceUUID,
            @JsonProperty("deviceName")     String deviceName,
            @JsonProperty("deviceLocation") String deviceLocation,
            @JsonProperty("deviceStatus") DeviceStatus deviceStatus,
            @JsonProperty("deviceTemperature") Integer deviceTemperature) {
        super(deviceUUID, deviceName, deviceLocation, deviceStatus);
        this.deviceTemperature = deviceTemperature;
    }

    @JsonGetter("deviceTemperature")
    public Integer getDeviceTemperature() {
        return deviceTemperature;
    }
}
