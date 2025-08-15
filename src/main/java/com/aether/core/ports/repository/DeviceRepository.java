package com.aether.core.ports.repository;

import com.aether.core.types.devices.AbstractDevice;
import com.aether.core.types.wrappers.Location;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Интерфейс репозитория Device'ов.
 * <p>Предоставляет исключительно методы взаимодействия.</p>
 * <p>В качестве реализации будет использоваться Map>UUID, Device< с потокобезопасным интерфейсом</p>
 */
public interface DeviceRepository {
    /**
     * Add new device to repository
     * @param device Device extends AbstractDevice
     * @see AbstractDevice
     */
    void addDevice(AbstractDevice device);

    /**
     * Remove device from repository
     * @param deviceUUID Device UUID
     */
    void removeDevice(UUID deviceUUID);

    /**
     * Get device by device UUID
     * @param uuid Device UUID
     * @return Device as AbstractDevice
     */
    AbstractDevice getDeviceBtUUID(UUID uuid);

    /**
     * Get all devices
     * @return List of devices
     */
    List<AbstractDevice> getAllDeviceList();

    /**
     * Get all devices
     * @return Map of devices
     */
    Map<UUID, AbstractDevice> getAllDeviceMap();

    /**
     * Get devices by Location
     * @param location Location
     * @return List of devices
     * @see Location
     */
    List<AbstractDevice> getDeviceByLocation(Location location);

    /**
     * Get Devices by device type
     * @param deviceType Type of device class
     * @return List of devices as T
     */
    List<AbstractDevice> getDeviceByType(String deviceType);
}
