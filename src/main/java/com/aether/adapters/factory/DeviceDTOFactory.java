package com.aether.adapters.factory;

import com.aether.core.ports.functionalInterfaces.DeviceDTOCreator;
import com.aether.core.types.devices.AbstractDevice;
import com.aether.dto.DeviceDTO;
import com.aether.exceptions.DTOFactoryException;
import com.aether.exceptions.DeviceRegistryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class DeviceDTOFactory {
    private static final Logger logger = LoggerFactory.getLogger(DeviceDTOFactory.class);
    private static volatile DeviceDTOFactory instance;
    private static final Object lock = new Object();
    private final DeviceDTORegistry deviceDTORegistry;

    private DeviceDTOFactory() {
        this.deviceDTORegistry = DeviceDTORegistry.getInstance();
        logger.info("DTO factory initialized");
    }

    public static DeviceDTOFactory getInstance() {
        if (instance == null) {
            synchronized (lock) {
                instance = new DeviceDTOFactory();
            }
        }
        return instance;
    }

    public boolean isDeviceTypeSupported(String deviceType) {
        return deviceDTORegistry.contains(deviceType);
    }

    public Set<String> getSupportedDevices() {
        return deviceDTORegistry.getRegisteredTypes();
    }

    public DeviceDTO createDTO(AbstractDevice device) {
        Objects.requireNonNull(device, "Device cannot be null");
        String deviceType = device.getClass().getSimpleName();
        try {
            DeviceDTOCreator creator = deviceDTORegistry.getTransform(deviceType);
            DeviceDTO deviceDTO = creator.transform(device);

            logger.trace("Successfully created DTO for device type \"{}\" with UUID: {}", deviceType, device.getDeviceUUID());
            return deviceDTO;
        } catch (DeviceRegistryException e) {
            logger.error("Failed to create DTO for device type \"{}\": {}", deviceType, e.getMessage());
            throw new DeviceRegistryException("Unknown device type for DTO conversion: " + deviceType +
                    ". Available types: " + deviceDTORegistry.getRegisteredTypes());
        } catch (Exception e) {
            logger.error("Error creating DTO for device type \"{}\": {}", deviceType, e.getMessage());
            throw new DTOFactoryException("DTO creation failed for device type: " + deviceType);
        }
    }

    public List<DeviceDTO> createDTOList(List<AbstractDevice> deviceList) {
        Objects.requireNonNull(deviceList, "Devices list cannot be null");
        return deviceList.stream().filter(Objects::nonNull).map(this::createDTO).toList();
    }
}
