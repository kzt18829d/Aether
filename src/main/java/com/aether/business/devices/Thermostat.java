package com.aether.business.devices;

import com.aether.business.types.Location;
import com.aether.business.types.Name;
import com.aether.business.types.Temperature;

import java.util.Objects;

public class Thermostat extends Device {
    private final Temperature deviceTemperature;


    /**
     * Конструктор для преобразования из JSON/db
     *
     * @param deviceUUID
     * @param deviceName
     * @param deviceLocation
     * @param deviceStatus
     */
    protected Thermostat(String deviceUUID, String deviceName, String deviceLocation, String deviceStatus, Integer deviceTemperature) {
        super(deviceUUID, deviceName, deviceLocation, deviceStatus);
        this.deviceTemperature = new Temperature(deviceTemperature);
    }

    /**
     * Основной конструктор
     *
     * @param deviceName
     * @param deviceLocation
     */
    protected Thermostat(Name deviceName, Location deviceLocation, Temperature deviceTemperature) {
        super(deviceName, deviceLocation);
        this.deviceTemperature = deviceTemperature;
    }

    /**
     * Минимальный конструктор конструктор
     *
     * @param deviceName
     * @param deviceLocation
     */
    protected Thermostat(Name deviceName, Location deviceLocation) {
        super(deviceName, deviceLocation);
        this.deviceTemperature = new Temperature(Temperature.BASED);
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
