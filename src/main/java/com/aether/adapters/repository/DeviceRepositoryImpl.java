package com.aether.adapters.repository;

import com.aether.core.ports.repository.DeviceRepository;
import com.aether.core.types.devices.AbstractDevice;
import com.aether.core.types.wrappers.Location;
import com.aether.exceptions.RepositoryException;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class DeviceRepositoryImpl implements DeviceRepository {

    private Map<UUID, AbstractDevice> repositoryMap;

    /**
     * Based Constructor
     */
    public DeviceRepositoryImpl() {
        this.repositoryMap = new ConcurrentHashMap<>();
    }

    /**
     * Add new device to repository
     *
     * @param device Device extends AbstractDevice
     * @see AbstractDevice
     * @throws NullPointerException If device is null
     * @throws RepositoryException Device was added earlier
     */
    @Override
    public void addDevice(AbstractDevice device) {
        if (device == null) throw new NullPointerException("Device cannot be null");
        var uuid = device.getDeviceUUID();
        if (repositoryMap.containsKey(uuid)) throw new RepositoryException("Device " + uuid + " was added earlier");
        repositoryMap.putIfAbsent(uuid, device);
    }

    /**
     * Remove device from repository
     *
     * @param deviceUUID Device UUID
     * @throws NullPointerException Device UUID cannot be null
     * @throws RepositoryException Empty UUID or Device not found
     */
    @Override
    public void removeDevice(UUID deviceUUID) {
        if (deviceUUID == null) throw new NullPointerException("Device UUID cannot be null");
        if (deviceUUID.toString().isEmpty()) throw new RepositoryException("UUID cannot be empty");
        if (!repositoryMap.containsKey(deviceUUID)) throw new RepositoryException("Device with UUID " + deviceUUID + " not found");
        repositoryMap.remove(deviceUUID);
    }

    /**
     * Get device by device UUID
     *
     * @param uuid Device UUID
     * @return Device as AbstractDevice or null
     */
    @Override
    public AbstractDevice getDeviceBtUUID(UUID uuid) {
        return repositoryMap.get(uuid);
    }

    /**
     * Get all devices
     *
     * @return List of devices
     */
    @Override
    public List<AbstractDevice> getAllDeviceList() {
        return repositoryMap.values().stream().toList();
    }

    /**
     * Get all devices
     *
     * @return Map of devices
     */
    @Override
    public Map<UUID, AbstractDevice> getAllDeviceMap() {
        return repositoryMap;
    }

    /**
     * Get devices by Location
     *
     * @param location Location
     * @return List of devices
     * @throws NullPointerException Null Location
     * @see Location
     */
    @Override
    public List<AbstractDevice> getDeviceByLocation(Location location) {
        if (location == null) throw new NullPointerException("Location cannot be null");
        return repositoryMap.values().stream().filter(d -> d.getLocation() == location).toList();
    }

    /**
     * Get Devices by device type
     *
     * @param deviceType Type of device class
     * @return List of devices as T
     */
    @Override
    public List<AbstractDevice> getDeviceByType(String deviceType) {
        return repositoryMap.values().stream().filter(d -> deviceType.equals(d.getClass().getSimpleName())).toList();
    }
}
