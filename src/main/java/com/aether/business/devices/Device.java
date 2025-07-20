package com.aether.business.devices;

import java.util.Objects;
import java.util.UUID;
import com.aether.business.devices.SubTypes.Location;
import com.aether.business.devices.SubTypes.Name;
import com.aether.business.enums.Status;

/**
 * Абстрактный класс Device. Родитель прочих устройств умного дома
 *
 * @see Location
 * @see Name
 * */
public abstract class Device {

    /// Идентификатор устройства
    private final UUID uuid;
    /// Имя устройства
    private final Name deviceName;
    /// Местоположение устройства
    private Location location;

    Status status;

    /// @param deviceName Имя устройства
    /// @param location местоположение устройства
    protected Device(Name deviceName, Location location) {
        this.uuid = UUID.randomUUID();
        this.deviceName = deviceName;
        this.location = location;
    }


    /// Функция включения устройства
    public final boolean turnOn() {
        try {
            status = Status.ONLINE;
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /// Функция выключения устройства
    public final boolean turnOff() {
        try{
            status = Status.OFFLINE;
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public final Status getStatus() {
        return status;
    }

    public final String getDeviceNameString() {
        return deviceName.getName();
    }

    public final Name getDeviceName() {
        return deviceName;
    }

    public final Location getLocation() {
        return location;
    }

    public final String getLocationString() {
        return location.getLocation();
    }

    public final UUID getUuid() {
        return uuid;
    }

    /// Перемещение устройства
    /// @param other Локация, куда переместить
    public boolean relocate(Location other) {
        location = other;
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Device device = (Device) o;
        return Objects.equals(uuid, device.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(uuid);
    }
}
