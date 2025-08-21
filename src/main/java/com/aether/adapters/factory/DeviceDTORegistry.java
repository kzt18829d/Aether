package com.aether.adapters.factory;


import com.aether.core.ports.functionalInterfaces.DeviceDTOCreator;
import com.aether.core.types.devices.Light;
import com.aether.core.types.devices.SecurityCamera;
import com.aether.core.types.devices.SmartLock;
import com.aether.core.types.devices.Thermostat;
import com.aether.dto.LightDTO;
import com.aether.dto.SecurityCameraDTO;
import com.aether.dto.SmartLockDTO;
import com.aether.dto.ThermostatDTO;
import com.aether.exceptions.DTORegistryException;
import com.aether.exceptions.DeviceRegistryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static com.aether.core.logging.LoggerMarkers.*;

public class DeviceDTORegistry {
    private static final Logger logger = LoggerFactory.getLogger(DeviceDTORegistry.class);

    private static volatile DeviceDTORegistry instance;
    private static final Object lock = new Object();

    private final Map<String, DeviceDTOCreator> registryMap = new ConcurrentHashMap<>();

    private DeviceDTORegistry() {
        registerDefaultDTOConverters();
        logger.info("DTO registry initialized with default converters");
    }

    public static DeviceDTORegistry getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) instance = new DeviceDTORegistry();
            }
        }
        return instance;
    }

    public void registry(String typeName, DeviceDTOCreator creator) {
        Objects.requireNonNull(typeName, "Type name cannot be null");
        Objects.requireNonNull(creator, "Creator function cannot be null");

        if (typeName.trim().isEmpty()) {
            throw new DTORegistryException("Type name cannot be empty");
        }
        registryMap.put(typeName, creator);
    }

    public DeviceDTOCreator getTransform(String typeName) {
        DeviceDTOCreator creator = registryMap.get(typeName);
        if (creator == null) throw new DTORegistryException("No DTO converter registered for device type: " + typeName);
        return creator;
    }

    public boolean contains(String key) {
        return registryMap.containsKey(key);
    }

    public Set<String> getRegisteredTypes() {
        return registryMap.keySet();
    }

    private void registerDefaultDTOConverters() {
        registry("Light", device -> {
            if (!(device instanceof Light light)) {
                throw new IllegalArgumentException("Expected Light device, but got: " + device.getClass().getSimpleName());
            }

            return new LightDTO(
                    light.getDeviceUUID(),
                    light.getDeviceName().getString(),
                    light.getLocation() != null ? light.getLocation().getString() : null,
                    light.getDeviceStatus(),
                    light.getDeviceBrightness().getBrightness(),
                    light.getDeviceColorTemperature().getColorTemperature()
            );
        });

        registry("SecurityCamera", device -> {
            if (!(device instanceof SecurityCamera camera)) {
                throw new IllegalArgumentException("Expected SecurityCamera device, but got: " + device.getClass().getSimpleName());
            }

            return new SecurityCameraDTO(
                    camera.getDeviceUUID(),
                    camera.getDeviceName().getString(),
                    camera.getLocation() != null ? camera.getLocation().getString() : null,
                    camera.getDeviceStatus(),
                    camera.getRecordingStatus(),
                    camera.getNightVisionStatus()
            );
        });

        registry("SmartLock", device -> {
            if (!(device instanceof SmartLock lock)) {
                throw new IllegalArgumentException("Expected SmartLock device, but got: " + device.getClass().getSimpleName());
            }

            return new SmartLockDTO(
                    lock.getDeviceUUID(),
                    lock.getDeviceName().getString(),
                    lock.getLocation() != null ? lock.getLocation().getString() : null,
                    lock.getDeviceStatus(),
                    lock.getLockStatus()
            );
        });

        registry("Thermostat", device -> {
            if (!(device instanceof Thermostat thermostat)) {
                throw new IllegalArgumentException("Expected Thermostat device, but got: " + device.getClass().getSimpleName());
            }

            return new ThermostatDTO(
                    thermostat.getDeviceUUID(),
                    thermostat.getDeviceName().getString(),
                    thermostat.getLocation() != null ? thermostat.getLocation().getString() : null,
                    thermostat.getDeviceStatus(),
                    thermostat.getTemperature().getTemperature()
            );
        });
    }

}
