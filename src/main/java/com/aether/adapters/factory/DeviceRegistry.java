package com.aether.adapters.factory;

import com.aether.core.ports.functionalInterfaces.DeviceCreator;
import com.aether.core.types.devices.Light;
import com.aether.core.types.devices.SecurityCamera;
import com.aether.core.types.devices.SmartLock;
import com.aether.core.types.devices.Thermostat;
import com.aether.core.types.enums.DeviceStatus;
import com.aether.core.types.enums.LockStatus;
import com.aether.core.types.enums.NightVisionStatus;
import com.aether.core.types.enums.RecordingStatus;
import com.aether.core.types.wrappers.*;
import com.aether.core.validators.BrightnessValidator;
import com.aether.core.validators.ColorTemperatureValidator;
import com.aether.core.validators.NameValidator;
import com.aether.core.validators.TemperatureValidator;
import com.aether.exceptions.DeviceRegistryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import static com.aether.core.logging.LoggerMarkers.*;

public class DeviceRegistry {
    private static final Logger logger = LoggerFactory.getLogger(DeviceRegistry.class.getName());
    private static volatile DeviceRegistry instance;
    private static final Object lock = new Object();

    private final Map<String, DeviceCreator> registryMap = new ConcurrentHashMap<>();

    private DeviceRegistry() {
        registerDefaultDeviceTypes();
        logger.info("Device registry initialized with default device types");
    }

    public static DeviceRegistry getInstance() {
        if (instance == null){
            synchronized (lock) {
                instance =  new DeviceRegistry();
            }
        }
        return instance;
    }

    public boolean contains(String key) {
        return registryMap.containsKey(key);
    }

    public void removeRegistry(String key) {
        if (!contains(key)) throw new DeviceRegistryException("Device key \"" + key + "\" not found");
        registryMap.remove(key);
        logger.info("Device type \"{}\" removed from registry", key);
    }

    public DeviceCreator getRegistry(String key) {
        DeviceCreator creator = registryMap.get(key);
        if (creator == null) throw new DeviceRegistryException("No creator registered for device type: " + key);
        return creator;
    }

    public Set<String> getRegisteredTypes() {
        return registryMap.keySet();
    }

    private Name createName(String name) {
        return new Name(NameValidator.validate(name));
    }

    private Location createLocation(String name) {
        return new Location(NameValidator.validate(name));
    }

    private Brightness createBrightness(Integer brightness) {
        return new Brightness(BrightnessValidator.validate(brightness));
    }

    private ColorTemperature createColorTemperature(Integer temperature) {
        return new ColorTemperature(ColorTemperatureValidator.validate(temperature));
    }

    private Temperature createTemperature(Integer temperature) {
        return new Temperature(TemperatureValidator.validate(temperature));
    }

    public void registry(String typeName, DeviceCreator deviceCreator) {
        Objects.requireNonNull(typeName, "TypeName cannot be null");
        Objects.requireNonNull(deviceCreator, "Function cannot be null");

        if (typeName.trim().isEmpty()) throw new DeviceRegistryException("Typename cannot be empty");
        if (registryMap.put(typeName, deviceCreator) != null) {
            logger.warn("Device type \"{}\" was already registered and has been overridden", typeName);
        } else logger.info("Device type \"{}\" registered successfully", typeName);
    }

    private void registerDefaultDeviceTypes() {
        registry("Light", parameters -> {
            // Поддержка двух режимов создания: runtime и deserialization
            if (parameters.containsKey("deviceUUID")) {
                // Deserialization constructor - восстанавливаем из сохранённых данных
                return new Light(
                        (UUID) parameters.get("deviceUUID"),
                        createName((String) parameters.get("deviceName")),
                        createLocation((String) parameters.get("deviceLocation")),
                        (DeviceStatus) parameters.get("deviceStatus"),
                        createBrightness((Integer) parameters.get("deviceBrightness")),
                        createColorTemperature((Integer) parameters.get("deviceColorTemperature"))
                );
            } else {
                // Runtime constructor - создание нового устройства
                return new Light(
                        createName((String) parameters.get("deviceName")),
                        createLocation((String) parameters.get("deviceLocation")),
                        createBrightness((Integer) parameters.get("deviceBrightness")),
                        createColorTemperature((Integer) parameters.get("deviceColorTemperature"))
                );
            }
        });

        registry("SecurityCamera", parameters -> {
            if (parameters.containsKey("deviceUUID")) {
                return new SecurityCamera(
                        (UUID) parameters.get("deviceUUID"),
                        createName((String) parameters.get("deviceName")),
                        createLocation((String) parameters.get("deviceLocation")),
                        (DeviceStatus) parameters.get("deviceStatus"),
                        (NightVisionStatus) parameters.get("deviceNightVisionStatus"),
                        (RecordingStatus) parameters.get("deviceRecordingStatus")
                );
            } else {
                return new SecurityCamera(
                        createName((String) parameters.get("deviceName")),
                        createLocation((String) parameters.get("deviceLocation"))
                );
            }
        });

        registry("SmartLock", parameters -> {
            if (parameters.containsKey("deviceUUID")) {
                return new SmartLock(
                        (UUID) parameters.get("deviceUUID"),
                        createName((String) parameters.get("deviceName")),
                        createLocation((String) parameters.get("deviceLocation")),
                        (DeviceStatus) parameters.get("deviceStatus"),
                        (LockStatus) parameters.get("deviceLockStatus")
                );
            } else {
                return new SmartLock(
                        createName((String) parameters.get("deviceName")),
                        createLocation((String) parameters.get("deviceLocation"))
                );
            }
        });

        registry("Thermostat", parameters -> {
            if (parameters.containsKey("deviceUUID")) {
                return new Thermostat(
                        (UUID) parameters.get("deviceUUID"),
                        createName((String) parameters.get("deviceName")),
                        createLocation((String) parameters.get("deviceLocation")),
                        (DeviceStatus) parameters.get("deviceStatus"),
                        createTemperature((Integer) parameters.get("deviceTemperature"))
                );
            } else {
                return new Thermostat(
                        createName((String) parameters.get("deviceName")),
                        createLocation((String) parameters.get("deviceLocation")),
                        createTemperature((Integer) parameters.get("deviceTemperature"))
                );
            }
        });
    }
}
