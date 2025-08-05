package com.aether.business.devices;

import com.aether.business.Exceptions.valid.DeviceException;
import com.aether.business.devices.deviceInterfaces.Relocation;
import com.aether.business.devices.deviceInterfaces.TurnPower;
import com.aether.business.enums.DeviceStatus;
import com.aether.business.types.Location;
import com.aether.business.types.Name;

import java.util.Objects;
import java.util.UUID;

/**
 * Абстрактный класс Device, родительский класс устройств умного дома.
 * @see Name
 * @see Location
 * @see DeviceStatus
 * @see TurnPower
 * @see Relocation
 */
public abstract class Device implements TurnPower<DeviceStatus>, Relocation {
    /**
     * UUID устройства
     * Название устройства
     * Местоположение устройства
     * Статус устройства
     */
    private final UUID deviceUUID;
    private final Name deviceName;
    private Location deviceLocation;
    DeviceStatus deviceStatus;


    /**
     * Конструктор для преобразования из JSON/db
     * @param deviceUUID
     * @param deviceName
     * @param deviceLocation
     * @param deviceStatus
     */
    protected Device(String deviceUUID, String deviceName, String deviceLocation, String deviceStatus) {
        this.deviceUUID = UUID.fromString(deviceUUID);
        this.deviceName = new Name(deviceName);
        this.deviceLocation = new Location(deviceLocation);
        this.deviceStatus = DeviceStatus.fromString(deviceStatus);
    }

    /**
     * Основной конструктор
     * @param deviceName
     * @param deviceLocation
     */
    protected Device(Name deviceName, Location deviceLocation) {
        this.deviceUUID = UUID.randomUUID();
        this.deviceName = deviceName;
        this.deviceLocation = deviceLocation;
        this.deviceStatus = DeviceStatus.ONLINE;
    }

    /**
     * Device name getter
     * @return Name
     */
    public final Name getDeviceName() { return deviceName; }

    /**
     * Device location getter
     * @return Location
     */
    public final Location getDeviceLocation() { return deviceLocation; }

    /**
     * Device UUID getter
     * @return UUID
     */
    public final UUID getDeviceUUID() { return deviceUUID; }

    /**
     * Device location getter (to String)
     * @return String
     */
    public final String getDeviceLocation_string() { return deviceLocation.getString(); }

    /**
     * Device name getter (to String)
     * @return String
     */
    public final String getDeviceName_string() { return deviceName.getString(); }

    /**
     * Device location setter
     * @param deviceLocation
     */
    public void setDeviceLocation(Location deviceLocation) {
        if (this.deviceLocation != null) throw new DeviceException("The device is already linked to the location");
        this.deviceLocation = deviceLocation;
    }

    /**
     * Включить устройство
     * @return boolean
     */
    @Override
    public boolean turnOn() {
        if (deviceLocation == null) return false;
        try {
            deviceStatus = DeviceStatus.ONLINE;
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Выключить устройство
     * @return boolean
     */
    @Override
    public final boolean turnOff() {
        try{
            deviceStatus = DeviceStatus.OFFLINE;
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Статус питания
     * @return DeviceStatus
     */
    public DeviceStatus getPowerStatus() {
        return this.deviceStatus;
    }

    /**
     * Релокация устройства
     * @param location
     */
    @Override
    public void relocate(Location location) {
        try {
            this.deviceLocation = location;
        } catch (Exception e) {
            throw new DeviceException("Device " + deviceUUID + " wasn't relocated");
        }
    }

    /**
     * Вспомогательный инструмент для генерации хэш-функций
     * @return int
     */
    protected int hashCoder() {
        return 31 * deviceUUID.hashCode() + deviceName.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Device device = (Device) o;
        return Objects.equals(deviceUUID, device.deviceUUID) && Objects.equals(deviceName, device.deviceName) && Objects.equals(deviceLocation, device.deviceLocation) && deviceStatus == device.deviceStatus;
    }

    @Override
    public int hashCode() {
        return hashCoder() + getClass().hashCode();
    }
}
