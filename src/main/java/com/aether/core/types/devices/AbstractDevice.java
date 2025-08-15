package com.aether.core.types.devices;

import com.aether.core.types.enums.DeviceStatus;
import com.aether.core.types.interfaces.Relocation;
import com.aether.core.types.interfaces.TurnDeviceStatus;
import com.aether.core.types.wrappers.Location;
import com.aether.core.types.wrappers.Name;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

/**
 *
 * Абстрактный класс устройств умного дома
 *
 */
public abstract class AbstractDevice implements TurnDeviceStatus, Relocation {
    private final UUID deviceUUID;
    private final Name deviceName;
    private AtomicReference<Location> deviceLocation;
    private final AtomicReference<DeviceStatus> deviceStatus;

    /**
     * Main Constructor
     * @param deviceName Name of device
     * @param deviceLocation Device location
     * @see Name
     * @see Location
     */
    public AbstractDevice(Name deviceName, Location deviceLocation) {
        this.deviceUUID = UUID.randomUUID();
        this.deviceName = deviceName;
        this.deviceLocation = new AtomicReference<>(deviceLocation);
        this.deviceStatus = new AtomicReference<>(DeviceStatus.ONLINE);
    }

    /**
     * Serialization from DTO objects Constructor
     * @param deviceUUID Device UUID
     * @param deviceName Device name
     * @param deviceLocation Device location
     * @param deviceStatus Device current Status
     */
    public AbstractDevice(UUID deviceUUID, Name deviceName, Location deviceLocation, DeviceStatus deviceStatus) {
        this.deviceUUID = deviceUUID;
        this.deviceName = deviceName;
        this.deviceLocation = new AtomicReference<>(deviceLocation);
        this.deviceStatus = new AtomicReference<>(deviceStatus);
    }

    public UUID getDeviceUUID() { return deviceUUID; }

    public Name getDeviceName() { return deviceName; }

    public Location getLocation() { return deviceLocation.get(); }

    @Override
    public DeviceStatus getDeviceStatus() { return deviceStatus.get(); }

    @Override
    public boolean turnStatusOn() {
        deviceStatus.set(DeviceStatus.ONLINE);
        return true;
    }

    @Override
    public boolean turnStatusOff() {
        deviceStatus.set(DeviceStatus.OFFLINE);
        return true;
    }

    /**
     *
     * @param location Location
     * @return true если локация привязалась, иначе false
     * @throws NullPointerException Location is null
     */
    public boolean linkLocation(Location location) {
        Objects.requireNonNull(location, "Location cannot be null");
        return deviceLocation.compareAndSet(null, location);
    }

    @Override
    public void relocate(Location location) {
        Objects.requireNonNull(location, "Location cannot be null");
        deviceLocation.set(location);
    }

    public void unlinkLocation() {
        deviceLocation.set(null);
    }

    public String getDeviceType() {
        return getClass().getSimpleName();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || this.getClass() != obj.getClass()) return false;
        AbstractDevice other = (AbstractDevice) obj;
        return this.deviceUUID.equals(other.deviceUUID);
    }

    @Override
    public int hashCode() {
        return 31 * deviceUUID.hashCode() + getClass().hashCode();
    }
}
