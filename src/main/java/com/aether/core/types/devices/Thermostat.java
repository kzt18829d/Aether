package com.aether.core.types.devices;

import com.aether.core.types.enums.DeviceStatus;
import com.aether.core.types.wrappers.Location;
import com.aether.core.types.wrappers.Name;
import com.aether.core.types.wrappers.Temperature;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

public class Thermostat extends AbstractDevice {
    protected AtomicReference<Temperature> deviceTemperature;

    /**
     * Основной конструктор
     *
     * @param deviceName
     * @param deviceLocation
     */
    public Thermostat(Name deviceName, Location deviceLocation, Temperature deviceTemperature) {
        super(deviceName, deviceLocation);
        this.deviceTemperature = new AtomicReference<>(deviceTemperature);
    }

    /**
     * Конструктор десериализации
     * @param deviceUUID
     * @param deviceName
     * @param deviceLocation
     * @param deviceStatus
     * @param deviceTemperature
     */
    public Thermostat(
            UUID deviceUUID,
            Name deviceName,
            Location deviceLocation,
            DeviceStatus deviceStatus,
            Temperature deviceTemperature) {
        super(deviceUUID, deviceName, deviceLocation, deviceStatus);
        this.deviceTemperature = new AtomicReference<>(deviceTemperature);
    }

    /**
     * Device temperature getter
     * @return Temperature
     */
    public Temperature getTemperature() {
        return deviceTemperature.get();
    }

    /**
     * Device temperature setter
     * @param temperature
     */
    public void setTemperature(Integer temperature) {
        this.deviceTemperature.get().setTemperature(temperature);
    }

}
