package com.aether.business.devices;

import com.aether.business.enums.DeviceStatus;
import com.aether.business.types.Location;
import com.aether.business.types.Name;
import com.aether.business.types.Temperature;
import com.fasterxml.jackson.annotation.*;

import java.util.Objects;
import java.util.UUID;

@JsonTypeName("Thermostat")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Thermostat extends Device {
    private final Temperature deviceTemperature;


    /**
     * Основной конструктор
     *
     * @param deviceName
     * @param deviceLocation
     */
    public Thermostat(Name deviceName, Location deviceLocation, Temperature deviceTemperature) {
        super(deviceName, deviceLocation);
        this.deviceTemperature = deviceTemperature;
    }

    /**
     * Минимальный конструктор конструктор
     *
     * @param deviceName
     * @param deviceLocation
     */
    public Thermostat(Name deviceName, Location deviceLocation) {
        super(deviceName, deviceLocation);
        this.deviceTemperature = new Temperature(Temperature.BASED);
    }

    /**
     * Конструктор десериализации
     * @param deviceUUID
     * @param deviceName
     * @param deviceLocation
     * @param deviceStatus
     * @param deviceTemperature
     */
    @JsonCreator
    public Thermostat(
            @JsonProperty("deviceUUID")         UUID deviceUUID,
            @JsonProperty("deviceName")         Name deviceName,
            @JsonProperty("deviceLocation")     Location deviceLocation,
            @JsonProperty("powerStatus")       DeviceStatus deviceStatus,
            @JsonProperty("deviceTemperature")  Temperature deviceTemperature) {
        super(deviceUUID, deviceName, deviceLocation, deviceStatus);
        this.deviceTemperature = deviceTemperature;
    }

    /**
     * Device temperature getter
     * @return Integer
     */
    public Integer getTemperature_int() {
        return deviceTemperature.getTemperature();
    }

    /**
     * Device temperature getter
     * @return Temperature
     */
    @JsonGetter("deviceTemperature")
    public Temperature getTemperature() {
        return deviceTemperature;
    }

    /**
     * Device temperature setter
     * @param temperature
     */
    public void setTemperature(Integer temperature) {
        this.deviceTemperature.setTemperature(temperature);
    }

    @Override
    public String getDeviceType() {
        return "Thermostat";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Thermostat that = (Thermostat) o;
        return Objects.equals(deviceTemperature, that.deviceTemperature);
    }

    @Override
    public int hashCode() {
        return hashCoder() + getClass().hashCode();
    }
}
